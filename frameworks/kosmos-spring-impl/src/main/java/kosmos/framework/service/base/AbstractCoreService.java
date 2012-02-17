/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import javax.persistence.OptimisticLockException;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.OrmQueryWrapperFactory;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.query.IdentifierGenerator;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.PersistenceManager;
import kosmos.framework.sqlclient.api.exception.UniqueConstraintException;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring用サービス.
 * 主にDeveloper向けのシンタックスシュガーを提供する。
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractCoreService extends AbstractService{

	/** ORMクエリファクトリ */
	@Autowired
	private OrmQueryWrapperFactory ormQueryFactory;
	
	/** クエリファクトリ */
	@Autowired
	private QueryFactory queryFactory;
	
	/** 永続化処理 */
	@Autowired
	private PersistenceManager persister;
	
	/** 非同期メッセージクライアントファクトリ */
	@Autowired
	private MessageClientFactory messageClientFactory;
	
	/** ID生成 */
	@Autowired
	private IdentifierGenerator defaultIdGenerator;
	
	/**
	 * P2P用非同期処理.
	 * 
	 * <pre>	
	 * OrderService service = createSender(OrderService.class);
	 * service.cancelOrder(paramter.getOrderId());
	 * 	
	 * トランザクション中に上記のようにコーディングしておくと、
	 * トランザクションコミット時に、待ち受けているMQのConsumerでOrderService#cancelメソッドが実行される。
	 * </pre>
	 * 
	 * @param serviceType 実行対象クラス
	 * @return サービス
	 */
	protected <T> T createSender(Class<T> serviceType){
		return messageClientFactory.createSender(serviceType);
	}
	
	/**
	 * M:N用非同期処理.
	 * 
	 * <pre>	
	 * OrderService service = createPublisher(OrderService.class);
	 * service.cancelOrder(paramter.getOrderId());
	 * 	
	 * トランザクション中に上記のようにコーディングしておくと、
	 * トランザクションコミット時に、サブスクライブしているMQのConsumerでOrderService#cancelメソッドが実行される。
	 * </pre>
	 * 
	 * @param serviceType 実行対象クラス
	 * @return サービス
	 */
	protected <T> T createPublisher(Class<T> serviceType){
		return messageClientFactory.createPublisher(serviceType);
	}
	
	/**
	 * ORM用のクエリクラスを生成する.
	 * 
	 * <pre>
	 * ◆複数件数取得
	 * EasyQuery&ltOneEntity&gt query = createEasyQuery(OneEntity.class);
	 * List&ltOneEntity&gt result = query.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).getResultList();
	 * for(OneEntity e : result){
	 * 		// process
	 * }
	 * ◆主キー検索
	 * EasyQuery&ltOneEntity&gt query = createEasyQuery(OneEntity.class);
	 * OneEntity result = query.find("key1","key2");
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V extends AbstractEntity,T extends EasyQuery<V>> T createEasyQuery(Class<V> entityClass){
		EasyQuery<V> query = ormQueryFactory.createEasyQuery(entityClass);		
		return (T)query;
	}
	
	/**
	 * ORM用の更新クラスを生成する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用せずupdate/deleteを使用すること.
	 * 
	 * <pre>
	 * ◆単純更新
	 * EasyUpdate&ltOneEntity&gt updater = createEasyUpdate(OneEntity.class);
	 * updater.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).set(OneEntity.ATTR3,"10");
	 * int result = updater.update();
	 *
	 * ◆バッチ更新
	 * EasyUpdate&ltOneEntity&gt updater = createOrmUpdate(OneEntity.class);
	 * for(int i = 0 ; i < 100; i++){
	 * 	updater.eq(OneEntity.ATTR1,i).set(OneEntity.ATTR3,i);
	 * 	updater.addBatch();
	 * }
	 * int[] result = updater.batchUpdate();
	 * 
	 * ※Oracleの場合、Oracleの仕様でbatchUpdateの結果配列にはすべて-2が入っているためSQL単位の更新件数は取得不可能
	 * 
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V extends AbstractEntity,T extends EasyUpdate<V>> T createEasyUpdate(Class<V> entityClass){
		EasyUpdate<V> query = ormQueryFactory.createEasyUpdate(entityClass);		
		return (T)query;
	}
	
	/**
	 * SQLファイルを使用した検索SQLを実行するクエリを取得する.
	 * 
	 * <pre>
	 * ◆単純検索
	 * OrderQuery query = createQuery(OrderQuery.class);
	 * List&ltOrderResult&gt result = query.setOrderCount(10).setOrderCategory("A").getResultList();
	 * for(OrderResult e : result){
	 * 	// process
	 * }
	 * 
	 * ◆件数取得(上限件数設定無効）
	 * OrderQuery query = createQuery(OrderQuery.class);
	 * int hitCount = query.setOrderCount(10).setOrderCategory("A").count();
	 *  
	 * ◆総件数取得検索（ページング用など）
	 * OrderQuery query = createQuery(OrderQuery.class);
	 * TotalResult result = query.setOrderCount(10).setOrderCategory("A").getTotalResult();
	 * boolean limited = result.isLimited();	--上限件数を超過していた場合のみtrue	
	 * List&ltOrderResult&gt = result.getResultList();	--条件件数までのデータのみ格納
	 * long hitCount = result.getHitCount();--実際にヒットした件数
	 * 
	 * ◆ フェッチ検索（帳票など）
	 * OrderQuery query = createQuery(OrderQuery.class);
	 *   
	 * </pre>
	 * 
	 * @param queryClass クエリクラス
	 * @return クエリ
	 */
	protected <T extends AbstractNativeQuery> T createQuery(Class<T> queryClass){
		return queryFactory.createQuery(queryClass);
	}
	
	/**
	 * SQLファイルを使用した更新SQLを実行するアップデータを取得する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用せずupdate/deleteを使用すること.
	 * 
	 * <pre>
	 * ◆単純更新
	 * OrderUpdate query = createUpdate(OrderUpdate.class);
	 * int result = query.setOrderCount(10).setOrderCategory("A").update();
	 *  
	 * ◆バッチ更新
	 * OrderUpdate updater = createUpdate(OrderUpdate.class);
	 * for(int i = 0 ; i < 100; i++){
	 * 	updater.setOrderCount(10).setOrderCategory("A");
	 * 	updater.addBatch();
	 * }
	 * int[] result = updater.batchUpdate();
	 * 
	 * ※Oracleの場合、Oracleの仕様でbatchUpdateの結果配列にはすべて-2が入っているためSQL単位の更新件数は取得不可能
	 *  
	 * </pre>
	 * 
	 * @param updateClass アップデータクラス
	 * @return アップデータ
	 */
	protected <T extends AbstractNativeUpdate> T createUpdate(Class<T> updateClass){
		return queryFactory.createUpdate(updateClass);
	}
	
	/**
	 * 主キー条件指定の単一行更新.<br/>
	 * 楽観ロックチェックを行う場合は当メソッド使用すること.<br/>
	 * 
	 * <pre>
	 * OrderEntity found = createEasyQuery(OrderEntity.class).setLockMode(LockModeType.PESSIMISTIC_LOCK).find("10");
	 * OrderEntity updatable = entity.clone();
	 * updatable.setItemCount(found.getItemCount-1);
	 * update(updatable,found);
	 * 
	 * foundと異なるupdatableの項目のみset値に含まれる。
	 * 上記の例だとitemCountのみset値に設定される。
	 * 更新条件は主キー限定。
	 * foundとupdatableでロック連番が異なる場合OptimisticLockExceptionをスローする。
	 * </pre>
	 * 
	 * @param entity 更新対象エンティティ（set値に含めるもの）
	 * @param found このトランザクションで検索したエンティティ
	 * @throws OptimisticLockException 楽観ロックエラー
	 * @return 更新件数
	 */
	protected <T extends AbstractEntity> int update(T entity, T found)throws OptimisticLockException {
		return persister.update(entity,createHints().setFoundEntity(found));
	}
	
	/**
	 * 主キー条件指定の単一行更新.<br/>
	 * 楽観ロックチェックを行う場合は当メソッド使用すること.<br/>
	 * 高速レスポンスが求められるか大量件数の更新でupdate前にfindすると性能が要求を満たせない場合に使用する。
	 * 
	 * <pre>
	 * OrderEntity entity = new OrderEntity().setId(1).setItemCount(10);
	 * update(entity);
	 * 
	 * NULL値はsetに含まれないため、上記の例だとitemCountのみset値に設定される。
	 * 更新条件は主キー限定。
	 * update実行結果0件の場合OptimisticLockExceptionをスローする。
	 * </pre>
	 * 
	 * @param entity 更新対象エンティティ
	 * @param hints 更新オプション
	 * @throws OptimisticLockException 楽観ロックエラー
	 * @return 更新件数
	 */
	protected <T extends AbstractEntity> int update(T entity,T found,PersistenceHints hints)
			throws OptimisticLockException {
		return persister.update(entity,hints.setFoundEntity(found));
	}
	
	/**
	 * 単一行挿入.<br/>
	 * 
	 * <pre>
	 * OrderEntity entity = new OrderEntity().setId(1).setItemCount(10)....;
	 * insert(entity);
	 * 
	 * NULL値はINSERT値に含まれないため、上記の例だとitemCountとidのみinsertのvalueに値が設定される
	 * insert時にすでにレコードが存在した場合OptimisticLockExceptionをスローする。
	 * </pre>
	 * 
	 * @param entity
	 * @param hints 更新オプション
	 * @return　挿入件数
	 * @throws UniqueConstraintException
	 */
	protected int insert(AbstractEntity entity,PersistenceHints hints) throws UniqueConstraintException{
		return persister.insert(entity,hints);
	}
	
	/**
	 * 主キー条件指定の単一行削除.<br/>
	 * 
	 * <pre>
	 * delete(new OrderEntity().setId(1));
	 * 
	 * 削除条件は主キー限定。
	 * delete実行結果0件の場合OptimisticLockExceptionをスローする。
	 * </pre>
	 * 
	 * @param entity 更新対象エンティティ
	 * @param hints 削除オプション
	 * @throws OptimisticLockException 楽観ロックエラー
	 * @return 削除件数
	 */
	protected int delete(AbstractEntity entity,PersistenceHints hints) throws OptimisticLockException{
		return persister.delete(entity,hints);
	}
	
	/**
	 * デフォルトのシーケンス生成エンジンを使用して新規IDを生成する。
	 * 
	 * <pre>
	 * 	OneEntity entity = new OneEntity();
	 * 	entity.setId(newId(SequenceName.ONE_NAME));
	 * </pre>
	 * 
	 * @param name シーケンス名
	 * @param arguments　シーケンスに使用する引数
	 * @return
	 */
	protected final long newId(String name,Object... arguments){
		return defaultIdGenerator.generate(name, arguments);
	}
	
	/**
	 * @return 永続化オプション
	 */
	protected PersistenceHints createHints(){
		return new PersistenceHints();
	}
	
}

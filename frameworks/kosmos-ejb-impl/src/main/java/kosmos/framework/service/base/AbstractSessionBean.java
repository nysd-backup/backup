/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.sqlclient.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.free.QueryFactory;
import kosmos.framework.sqlclient.orm.OrmQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.orm.OrmUpdate;

/**
 * EJB用サービス.
 * 主にDeveloper向けのシンタックスシュガーを提供する。
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractSessionBean extends AbstractService{

	/** ORMクエリファクトリ */
	private OrmQueryFactory ormQueryFactory;
	
	/** クエリファクトリ */
	private QueryFactory queryFactory;
	
	/** 永続化処理 */
	private EntityManager em;
	
	/** 非同期メッセージクライアントファクトリ */
	private MessageClientFactory messageClientFactory;
	
	/**
	 * 初期化処理後処理.
	 * コンストラクタで初期化した場合、他のSessionBeanがまだ作成済みになっていないので生成できない。
	 */
	@PostConstruct
	public void postConstruct(){
		ormQueryFactory = ServiceLocator.createDefaultOrmQueryFactory();
		queryFactory = ServiceLocator.createDefaultQueryFactory();
		em = ServiceLocator.lookupByInterface(EntityManagerProvider.class).getEntityManager();
		messageClientFactory = ServiceLocator.createDefaultMessageClientFactory();
	}
	
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
	 * OrmQuery&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * List&ltOneEntity&gt result = query.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).getResultList();
	 * for(OneEntity e : result){
	 * 		// process
	 * }
	 * ◆主キー検索
	 * OrmQuery&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * OneEntity result = query.find("key1","key2");
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V extends AbstractEntity,T extends OrmQuery<V>> T createOrmQuery(Class<V> entityClass){
		OrmQuery<V> query = ormQueryFactory.createQuery(entityClass);		
		return (T)query;
	}
	
	/**
	 * ORM用の更新クラスを生成する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用しないこと。
	 * また、更新時に永続化コンテキストはDBと同期がとれていない状態のため、次に検索するときは必ずDBから検索すること。
	 * 
	 * <pre>
	 * OrmUpdate&ltOneEntity&gt updater = createOrmUpdate(OneEntity.class);
	 * updater.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).set(OneEntity.ATTR3,"10");
	 * int result = updater.update();
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V extends AbstractEntity,T extends OrmUpdate<V>> T createOrmUpdate(Class<V> entityClass){
		OrmUpdate<V> query = ormQueryFactory.createUpdate(entityClass);		
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
	 * 単一行挿入.<br/>
	 * 
	 * <pre>
	 * OrderEntity entity = new OrderEntity().setId(1).setItemCount(10)....;
	 * persist(entity);
	 * </pre>
	 * 
	 * @param entity
	 */
	protected void persist(AbstractEntity entity){
		em.persist(entity);
	}
	
	/**
	 * 主キー条件指定の単一行削除.<br/>
	 * 
	 * <pre>
	 * XxxEntity entity = query.find("...");
	 * remove(entity);
	 * </pre>
	 * 
	 * @param entity 更新対象エンティティ
	 */
	protected void remove(AbstractEntity entity){
		em.remove(entity);
	}
	
	/**
	 * 永続化コンテキストからの切り離し.<br/>
	 * @param entity 更新対象エンティティ
	 */
	protected void detach(AbstractEntity entity){
		em.detach(entity);
	}
	
	/**
	 * コンテキストにたまっているエンティティをflushしてSQLを実行する。<br/>
	 * 楽観ロックエラーやSQL実行エラーなどはこのタイミングで発生する。
	 */
	protected void flush() {
		em.flush();
	}
		
}

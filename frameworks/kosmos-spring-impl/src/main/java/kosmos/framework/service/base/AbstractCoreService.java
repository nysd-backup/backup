/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.wrapper.free.QueryFactoryWrapper;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory;

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
	private QueryFactoryWrapper queryFactory;

	/** 非同期メッセージクライアントファクトリ */
	@Autowired
	private MessageClientFactory messageClientFactory;
	
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
	
}

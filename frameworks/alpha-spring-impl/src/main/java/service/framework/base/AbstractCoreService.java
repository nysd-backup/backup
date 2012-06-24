/**
 * Copyright 2011 the original author
 */
package service.framework.base;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import service.client.messaging.MessageClientFactory;
import client.sql.free.AbstractNativeSelect;
import client.sql.free.AbstractNativeUpsert;
import client.sql.free.QueryFactory;
import client.sql.orm.OrmQueryFactory;
import client.sql.orm.OrmSelect;
import client.sql.orm.OrmUpdate;
import core.base.AbstractEntity;


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
	private OrmQueryFactory ormQueryFactory;
	
	/** クエリファクトリ */
	@Autowired
	private QueryFactory queryFactory;

	/** 非同期メッセージクライアントファクトリ */
	@Autowired
	private MessageClientFactory messageClientFactory;
	
	/**
	 * @return the entity manager
	 */
	protected abstract EntityManager getEntityManger();
	
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
	 * OrmSelect&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * List&ltOneEntity&gt result = query.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).getResultList();
	 * for(OneEntity e : result){
	 * 		// process
	 * }
	 * ◆主キー検索
	 * OrmSelect&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * OneEntity result = query.find("key1","key2");
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V extends AbstractEntity,T extends OrmSelect<V>> T createOrmSelect(Class<V> entityClass){
		OrmSelect<V> query = ormQueryFactory.createSelect(entityClass,getEntityManger());		
		return (T)query;
	}
	
	/**
	 * ORM用の更新クラスを生成する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用せずupdate/deleteを使用すること.
	 * 
	 * <pre>
	 * ◆単純更新
	 * OrmUpdate&ltOneEntity&gt updater = createOrmUpdate(OneEntity.class);
	 * updater.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).set(OneEntity.ATTR3,"10");
	 * int result = updater.update();
	 *
	 * ◆バッチ更新
	 * OrmUpdate&ltOneEntity&gt updater = createOrmUpdate(OneEntity.class);
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
	protected <V extends AbstractEntity,T extends OrmUpdate<V>> T createOrmUpdate(Class<V> entityClass){
		OrmUpdate<V> query = ormQueryFactory.createUpdate(entityClass,getEntityManger());		
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
	protected <T extends AbstractNativeSelect> T createSelect(Class<T> queryClass){
		return queryFactory.createSelect(queryClass,getEntityManger());		
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
	protected <T extends AbstractNativeUpsert> T createUpsert(Class<T> updateClass){
		return queryFactory.createUpsert(updateClass,getEntityManger());
	}
	
}

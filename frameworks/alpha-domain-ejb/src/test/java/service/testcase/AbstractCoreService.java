/**
 * Copyright 2011 the original author
 */
package service.testcase;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;

import alpha.framework.domain.activation.AbstractEJBComponentFinder;
import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.transaction.DomainContext;
import alpha.sqlclient.free.AbstractNativeModifyQuery;
import alpha.sqlclient.free.AbstractNativeReadQuery;
import alpha.sqlclient.free.QueryFactory;
import alpha.sqlclient.orm.CriteriaModifyQuery;
import alpha.sqlclient.orm.CriteriaQueryFactory;
import alpha.sqlclient.orm.CriteriaReadQuery;



/**
 * EJB用サービス.
 * 主にDeveloper向けのシンタックスシュガーを提供する。
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractCoreService {

	@Resource
	private EJBContext context;
	
	/** ORMクエリファクトリ */
	private CriteriaQueryFactory ormQueryFactory;
	
	/** クエリファクトリ */
	private QueryFactory queryFactory;
	/**
	 * 初期化処理後処理.
	 * コンストラクタで初期化した場合、他のSessionBeanがまだ作成済みになっていないので生成できない。
	 */
	@PostConstruct
	public void postConstruct(){
		AbstractEJBComponentFinder finder = ServiceLocator.unwrap();		
		ormQueryFactory = finder.getQueryFactoryProvider().createCriteriaQueryFactory();
		queryFactory = finder.getQueryFactoryProvider().createQueryFactory();	
	}
	
	/**
	 * @see alpha.domain.framework.base.AbstractService#isRollbackOnly()
	 */
	protected boolean isRollbackOnly(){
		return DomainContext.getCurrentInstance().isRollbackOnly() || context.getRollbackOnly();
	}
	
	/**
	 * Gets the EntityManager.
	 * 
	 * <pre>
	 * 	@PersistenceContext(unitName="default")
	 *  private EntityManager em;
	 * </pre>
	 * @return EntityManager 
	 */
	protected abstract EntityManager getEntityManager();
	
	protected abstract ConnectionFactory getQueueConnectionFactory();
	
	protected abstract ConnectionFactory getTopicConnectionFactory();
	
//	/**
//	 * P2P用非同期処理.
//	 * 
//	 * <pre>	
//	 * OrderService alpha.domain = createSender(OrderService.class);
//	 * alpha.domain.cancelOrder(paramter.getOrderId());
//	 * 	
//	 * トランザクション中に上記のようにコーディングしておくと、
//	 * トランザクションコミット時に、待ち受けているMQのConsumerでOrderService#cancelメソッドが実行される。
//	 * </pre>
//	 * 
//	 * @param serviceType 実行対象クラス
//	 * @return サービス
//	 */
//	protected <T> T createSender(Class<T> serviceType){
//		MessagingProperty property = new MessagingProperty();
//		property.setConnectionFactory(getQueueConnectionFactory());
//		return messageClientFactory.createSender(serviceType,property);
//	}
//	
//	/**
//	 * M:N用非同期処理.
//	 * 
//	 * <pre>	
//	 * OrderService alpha.domain = createPublisher(OrderService.class);
//	 * alpha.domain.cancelOrder(paramter.getOrderId());
//	 * 	
//	 * トランザクション中に上記のようにコーディングしておくと、
//	 * トランザクションコミット時に、サブスクライブしているMQのConsumerでOrderService#cancelメソッドが実行される。
//	 * </pre>
//	 * 
//	 * @param serviceType 実行対象クラス
//	 * @return サービス
//	 */
//	protected <T> T createPublisher(Class<T> serviceType){
//		MessagingProperty property = new MessagingProperty();
//		property.setConnectionFactory(getTopicConnectionFactory());
//		return messageClientFactory.createPublisher(serviceType,property);
//	}
	
	/**
	 * ORM用のクエリクラスを生成する.
	 * 
	 * <pre>
	 * ◆複数件数取得
	 * CriteriaReadQuery&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * List&ltOneEntity&gt result = query.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).getResultList();
	 * for(OneEntity e : result){
	 * 		// process
	 * }
	 * ◆主キー検索
	 * CriteriaReadQuery&ltOneEntity&gt query = createOrmQuery(OneEntity.class);
	 * OneEntity result = query.find("key1","key2");
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V,T extends CriteriaReadQuery<V>> T createOrmSelect(Class<V> entityClass){
		CriteriaReadQuery<V> query = ormQueryFactory.createReadQuery(entityClass,getEntityManager());		
		return (T)query;
	}
	
	/**
	 * ORM用の更新クラスを生成する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用しないこと。
	 * また、更新時に永続化コンテキストはDBと同期がとれていない状態のため、次に検索するときは必ずDBから検索すること。
	 * 
	 * <pre>
	 * CriteriaModifyQuery&ltOneEntity&gt updater = createOrmUpdate(OneEntity.class);
	 * updater.eq(OneEntity.ATTR1,100).contains(OneEntity.ATTR2,"a","b","c).set(OneEntity.ATTR3,"10");
	 * int result = updater.update();
	 * </pre>
	 * 
	 * @param entityClass 対象エンティティ
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected <V,T extends CriteriaModifyQuery<V>> T createOrmUpdate(Class<V> entityClass){
		CriteriaModifyQuery<V> query = ormQueryFactory.createModifyQuery(entityClass,getEntityManager());		
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
	 * TotalData result = query.setOrderCount(10).setOrderCategory("A").getTotalResult();
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
	protected <T extends AbstractNativeReadQuery> T createSelect(Class<T> queryClass){
		T query = queryFactory.createReadQuery(queryClass,getEntityManager());
		return query;
	}
	
	/**
	 * SQLファイルを使用した更新SQLを実行するアップデータを取得する.
	 * 楽観ロックチェックを行う場合は当メソッドは使用せずupdate/deleteを使用すること.
	 * 
	 * <pre>
	 * 
	 * OrderUpdate query = createUpdate(OrderUpdate.class);
	 * int result = query.setOrderCount(10).setOrderCategory("A").update();
	 *
	 * </pre>
	 * 
	 * @param updateClass アップデータクラス
	 * @return アップデータ
	 */
	protected <T extends AbstractNativeModifyQuery> T createUpsert(Class<T> updateClass){
		T query = queryFactory.createModifyQuery(updateClass,getEntityManager());
		return query;
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
	protected void persist(Object entity){
		getEntityManager().persist(entity);
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
	protected void remove(Object entity){
		getEntityManager().remove(entity);
	}
	
	/**
	 * 永続化コンテキストからの切り離し.<br/>
	 * @param entity 更新対象エンティティ
	 */
	protected void detach(Object entity){
		getEntityManager().detach(entity);
	}
	
	/**
	 * コンテキストにたまっているエンティティをflushしてSQLを実行する。<br/>
	 * 楽観ロックエラーやSQL実行エラーなどはこのタイミングで発生する。
	 */
	protected void flush() {
		getEntityManager().flush();
	}
		
}

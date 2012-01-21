/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import javax.persistence.OptimisticLockException;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.PersistenceManager;
import kosmos.framework.sqlclient.api.exception.UniqueConstraintException;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基底サービス.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractCoreService extends AbstractService{

	/** ORMクエリファクトリ */
	@Autowired
	private LimitedOrmQueryFactory ormQueryFactory;
	
	/** クエリファクトリ */
	@Autowired
	private QueryFactory queryFactory;
	
	/** 永続化処理 */
	@Autowired
	private PersistenceManager persister;
	
	/** 非同期メッセージクライアントファクトリ */
	@Autowired
	private MessageClientFactory messageClientFactory;
	
	/**
	 * P2P用非同期処理
	 * 
	 * @param serviceType
	 * @return
	 */
	protected final <T> T createSender(Class<T> serviceType){
		return messageClientFactory.createSender(serviceType);
	}
	
	/**
	 * @param serviceType
	 * @return
	 */
	protected final <T> T createPublisher(Class<T> serviceType){
		return messageClientFactory.createPublisher(serviceType);
	}
	
	/**
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final <V extends AbstractEntity,T extends StrictQuery<V>> T createOrmQuery(Class<V> entityClass){
		StrictQuery<V> query = ormQueryFactory.createStrictQuery(entityClass);		
		return (T)query;
	}
	
	/**
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final <V extends AbstractEntity,T extends StrictUpdate<V>> T createOrmUpdate(Class<V> entityClass){
		StrictUpdate<V> query = ormQueryFactory.createStrictUpdate(entityClass);		
		return (T)query;
	}
	
	/**
	 * @param entityClass
	 * @return
	 */
	protected final <T extends AbstractNativeQuery> T createQuery(Class<T> queryClass){
		return queryFactory.createQuery(queryClass);
	}
	
	/**
	 * @param entityClass
	 * @return
	 */
	protected final <T extends AbstractNativeUpdate> T createUpdate(Class<T> updateClass){
		return queryFactory.createUpdate(updateClass);
	}
	
	/**
	 * @param entity
	 * @throws OptimisticLockException
	 * @return
	 */
	protected final <T extends AbstractEntity> int update(T entity)throws OptimisticLockException {
		return persister.update(entity);
	}
	
	/**
	 * @param entity
	 * @throws OptimisticLockException
	 * @return
	 */
	protected final <T extends AbstractEntity> int update(T entity,PersistenceHints hints)
			throws OptimisticLockException {
		return persister.update(entity);
	}
	
	/**
	 * @param entity
	 * @throws UniqueConstraintException
	 * @return
	 */
	protected final int insert(AbstractEntity entity) throws UniqueConstraintException{
		return persister.insert(entity);
	}
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 * @throws UniqueConstraintException
	 */
	protected final int insert(AbstractEntity entity,PersistenceHints hints) throws UniqueConstraintException{
		return persister.insert(entity,hints);
	}
	
	/**
	 * @param entity
	 * @return
	 */
	protected final int delete(AbstractEntity entity){
		return persister.delete(entity);
	}
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	protected final int delete(AbstractEntity entity,PersistenceHints hints){
		return persister.delete(entity,hints);
	}
	
	/**
	 * @return 
	 */
	protected final PersistenceHints createHints(){
		return new PersistenceHints();
	}
	
}

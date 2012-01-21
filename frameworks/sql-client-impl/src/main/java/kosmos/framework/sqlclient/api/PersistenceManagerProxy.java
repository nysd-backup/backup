/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;

/**
 * PersistenceManagerProxy.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceManagerProxy implements PersistenceManager{
	
	/** default manager */
	private PersistenceManager defaultManager;
	
	/** fast manager */
	private PersistenceManager fastManager;
	
	/** internal query */
	private InternalOrmQuery internalOrmQuery ;

	/**
	 * @param internaOrmlQuery the internaOrmlQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internalOrmQuery){
		this.internalOrmQuery = internalOrmQuery;
	}
	
	/**
	 * default init
	 */
	public void defaultInit(){
		defaultManager = new PersistenceManagerImpl();
		fastManager = new FastPersistenceManagerImpl();
		((PersistenceManagerImpl)defaultManager).setInternalOrmQuery(internalOrmQuery);
		((FastPersistenceManagerImpl)fastManager).setInternalOrmQuery(internalOrmQuery);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object)
	 */
	@Override
	public int insert(Object entity) {
		return insert(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object[])
	 */
	@Override
	public int[] insert(Object[] entity) {
		return insert(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object[], kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int[] insert(Object[] entity, PersistenceHints hints) {
		return entity[0] instanceof FastEntity ? fastManager.insert(entity,hints) : defaultManager.insert(entity,hints);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int insert(Object entity, PersistenceHints hints) {
		return entity instanceof FastEntity ? fastManager.insert(entity,hints) : defaultManager.insert(entity,hints);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#update(java.lang.Object)
	 */
	@Override
	public int update(Object entity) {
		return update(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#update(java.lang.Object, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int update(Object entity, PersistenceHints hints) {
		return entity instanceof FastEntity ? fastManager.update(entity,hints) : defaultManager.update(entity,hints);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object)
	 */
	@Override
	public int delete(Object entity) {
		return delete(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int delete(Object entity, PersistenceHints hints) {
		return entity instanceof FastEntity ? fastManager.delete(entity,hints) : defaultManager.delete(entity,hints);
	}

}

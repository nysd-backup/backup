/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;

/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryFactoryImpl implements OrmQueryFactory{
	
	/** the DAO */
	private InternalOrmQuery internalOrmQuery;
	
	/**
	 * @param internalOrmQuery the internalOrmQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internalOrmQuery){
		this.internalOrmQuery = internalOrmQuery;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass) {
		return (Q)new DefaultOrmQueryImpl<T>(entityClass,internalOrmQuery);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass){
		return (Q)new DefaultOrmUpdateImpl<T>(entityClass,internalOrmQuery);	
	}
	
}

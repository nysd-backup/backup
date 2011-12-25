/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlclient.internal.orm.impl.LocalOrmQueryEngine;
import kosmos.framework.sqlclient.internal.orm.impl.LocalOrmUpdateEngine;

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
		OrmQuery<T> engine = new LocalOrmQueryEngine<T>(entityClass,internalOrmQuery);
		return (Q)create(engine);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass){
		OrmUpdate<T> engine = new LocalOrmUpdateEngine<T>(entityClass,internalOrmQuery);
		return (Q)create(engine);
	}
	
	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param engine the internal engine
	 * @return the updater
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected OrmUpdate create(OrmUpdate engine ){
		return new DefaultOrmUpdateImpl(engine);
	}
	
	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param engine the internal engine
	 * @return the query
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected OrmQuery create(OrmQuery engine ){
		return new DefaultOrmQueryImpl(engine);
	}
	
}

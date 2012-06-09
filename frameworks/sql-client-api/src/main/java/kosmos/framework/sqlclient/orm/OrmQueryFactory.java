/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;

import kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery;


/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryFactory {
	
	/** the DAO */
	private InternalOrmQuery internalOrmQuery;
	
	/**
	 * @param internalOrmQuery the internalOrmQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internalOrmQuery){
		this.internalOrmQuery = internalOrmQuery;
	}

	
	/**
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> OrmSelect<T> createSelect(Class<T> entityClass){
		return new OrmSelect<T>(entityClass,internalOrmQuery);
	}
	
	/**
	 * Creates the updater.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> OrmUpsert<T> createUpsert(Class<T> entityClass){
		return new OrmUpsert<T>(entityClass,internalOrmQuery);
	}

}

/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.api.orm;

import javax.jdo.Extent;

import framework.sqlclient.api.orm.OrmQuery;

/**
 * The query for JDO.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JDOOrmQuery<T> extends OrmQuery<T>{

	/**
	 * Adds 'and' 
	 * @return　self
	 */
	public JDOOrmQuery<T> and();
	
	/**
	 * Adds 'or'
	 * @return　self
	 */
	public JDOOrmQuery<T> or();
	
	/**
	 * @return the extent
	 */
	public Extent<T> getExtent();
}

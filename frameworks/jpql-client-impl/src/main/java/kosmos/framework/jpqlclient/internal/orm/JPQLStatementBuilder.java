/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm;

import java.util.Collection;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;


/**
 * The builder to create the JPQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface JPQLStatementBuilder {

	/**
	 * Creates the SELECT statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createSelect(OrmQueryContext<?> condition);
	
	/**
	 * Creates the DELETE statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createDelete(OrmContext<?> condition);
	
	/**
	 * Creates the UPDATE statement.
	 * 
	 * @param condition the condition
	 * @param set the updating target
	 * @return　the statement
	 */
	public String createUpdate(OrmContext<?> condition, Collection<String> set) ;
	
}

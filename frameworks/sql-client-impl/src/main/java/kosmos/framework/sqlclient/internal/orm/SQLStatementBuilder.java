/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm;

import java.util.Collection;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;


/**
 * The builder to create the SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface SQLStatementBuilder {

	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createSelect(OrmQueryContext<?> condition);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createInsert(Class<?> entityClass, Collection<String> values);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createUpdate(OrmContext<?> condition, Collection<String> set);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createDelete(OrmContext<?> condition);
	
}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm;

import kosmos.framework.sqlclient.api.orm.OrmCondition;


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
	public String createSelect(OrmCondition<?> condition);
	
}

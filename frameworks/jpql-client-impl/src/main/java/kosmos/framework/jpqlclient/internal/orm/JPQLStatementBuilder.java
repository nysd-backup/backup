/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm;

import java.util.Collection;

import kosmos.framework.sqlclient.api.orm.OrmCondition;


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
	public String createSelect(OrmCondition<?> condition);
	
	
	/**
	 * Creates the DELETE statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createDelete(OrmCondition<?> condition);
	
	/**
	 * Creates the UPDATE statement.
	 * 
	 * @param condition the condition
	 * @param set the updating target
	 * @return　the statement
	 */
	public String createUpdate(OrmCondition<?> condition, Collection<String> set) ;
	
}

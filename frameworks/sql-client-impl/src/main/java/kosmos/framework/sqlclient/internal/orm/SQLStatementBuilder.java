/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm;

import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;


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
	public String createSelect(OrmQueryParameter<?> condition);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createInsert(Class<?> entityClass, Map<String,Object> values);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createUpdate(Class<?> entityClass,String filterString, List<WhereCondition> where, Map<String,Object> set);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createDelete(Class<?> entityClass,String filterString, List<WhereCondition> where);
	
	/**
	 * 
	 * @param filterString
	 * @param easyParams
	 */
	public void setConditionParameters(String filterString, Object[] easyParams, List<WhereCondition> condition , Bindable bindable);
	
	public static interface Bindable {
		
		/**
		 * @param key
		 * @param value
		 */
		public void setParameter(String key , Object value);
	}

}
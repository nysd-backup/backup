/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import java.util.Collection;
import java.util.List;

import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder;


/**
 * The builder to create the JPQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class JPQLStatementBuilderImpl extends AbstractStatementBuilder{

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createPrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createPrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("select e from %s e",entityClass.getSimpleName()));
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createDelete(java.lang.Class, java.lang.String, java.util.List)
	 */
	@Override
	public String createDelete(Class<?> entityClass,String filterString, List<WhereCondition> where){
		StringBuilder builder = new StringBuilder("delete e from ");
		builder.append(entityClass.getSimpleName()).append(" e ");
		builder.append(generateWhere(filterString,where));
		return builder.toString();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createUpdatePrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("update %s e ",entityClass.getSimpleName()));
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createInsert(java.lang.Class, java.util.Collection)
	 */
	@Override
	public String createInsert(Class<?> entityClass, Collection<String> values) {
		throw new UnsupportedOperationException();
	}

}

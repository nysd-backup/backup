/**
 * Copyright 2011 the original author
 */
package client.sql.elink.orm.strategy;

import java.util.List;
import java.util.Map;

import client.sql.orm.ExtractionCriteria;
import client.sql.orm.strategy.AbstractStatementBuilder;




/**
 * The builder to create the JPQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class JPQLStatementBuilderImpl extends AbstractStatementBuilder{

	/**
	 * @see client.sql.orm.strategy.AbstractStatementBuilder#createPrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createPrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("select e from %s e",entityClass.getSimpleName()));
	}

	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createDelete(java.lang.Class, java.lang.String, java.util.List)
	 */
	@Override
	public String createDelete(Class<?> entityClass,List<ExtractionCriteria> where){
		StringBuilder builder = new StringBuilder("delete e from ");
		builder.append(entityClass.getSimpleName()).append(" e ");
		builder.append(generateWhere(where));
		return builder.toString();
	}

	/**
	 * @see client.sql.orm.strategy.AbstractStatementBuilder#createUpdatePrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("update %s e ",entityClass.getSimpleName()));
	}

	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createInsert(java.lang.Class, java.util.Collection)
	 */
	@Override
	public String createInsert(Class<?> entityClass, Map<String,Object> values) {
		throw new UnsupportedOperationException();
	}

}

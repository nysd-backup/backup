/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm.impl;

import java.util.Collection;

import javax.persistence.Table;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;


/**
 * The builder to create the SQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
class SQLStatementBuilderImpl extends AbstractStatementBuilder implements SQLStatementBuilder{

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createPrefix(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@Override
	protected StringBuilder createPrefix(OrmQueryContext<?> condition) {
		StringBuilder builder = new StringBuilder("select * from ");
		return builder.append(condition.getEntityClass().getAnnotation(Table.class).name()).append(" e ");	
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createUpdatePrefix(kosmos.framework.sqlclient.api.orm.OrmContext)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(OrmContext<?> condition) {
		return new StringBuilder(String.format("update %s e ",condition.getEntityClass().getAnnotation(Table.class).name()));
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createInsert(java.lang.Class, java.util.Collection)
	 */
	@Override
	public String createInsert(Class<?> entityClass, Collection<String> keys) {
		
		StringBuilder builder = new StringBuilder("insert into ");
		String tableName = entityClass.getAnnotation(Table.class).name();
		builder.append(tableName).append(" ( ");
		boolean first = true;
		for(String key : keys){
			if(first){
				builder.append(key);
				first = !first;
			}else{
				builder.append(",").append(key);
			}
			builder.append("\n");
		}
		
		builder.append(" ) values ( ");
		first = true;
		for(String key : keys){
			if(first){
				builder.append(":").append(key);
				first = !first;
			}else{
				builder.append(",:").append(key);
			}
			builder.append("\n");
		}
		builder.append(" ) ");
		return builder.toString();
		
	}


	/**
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createDelete(kosmos.framework.sqlclient.api.orm.OrmContext)
	 */
	@Override
	public String createDelete(OrmContext<?> condition) {
		StringBuilder builder = new StringBuilder("delete from ");
		builder.append(condition.getEntityClass().getAnnotation(Table.class).name()).append(" ");
		return builder.append(generateWhere(condition)).toString();
	}


}

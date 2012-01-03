/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.Table;

import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder;


/**
 * The builder to create the SQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLStatementBuilderImpl extends AbstractStatementBuilder{

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#afterCreateSelect(java.lang.StringBuilder, kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	protected StringBuilder afterCreateSelect(StringBuilder query,OrmQueryParameter<?> condition){
		//悲観ロックの追加
		LockModeType lockModeType = condition.getLockModeType();
		if(LockModeType.PESSIMISTIC_READ == lockModeType){			
			query.append(" FOR UPDATE ");
			int timeout = 0;
			if(condition.getHints().containsKey(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT)){
				timeout = (Integer)condition.getHints().get(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT);				
			}
			if( timeout <= 0){
				query.append("NOWAIT");
			}else {
				query.append("WAIT ").append(timeout);
			}
		}
		return query;
		
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createPrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createPrefix(Class<?> entityClass) {
		StringBuilder builder = new StringBuilder("select * from ");
		return builder.append(entityClass.getAnnotation(Table.class).name()).append(" e ");	
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createUpdatePrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("update %s e ",entityClass.getAnnotation(Table.class).name()));
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
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createDelete(java.lang.Class, java.lang.String, java.util.List)
	 */
	@Override
	public String createDelete(Class<?> entityClass,String filterString,List<WhereCondition> where){
		StringBuilder builder = new StringBuilder("delete from ");
		builder.append(entityClass.getAnnotation(Table.class).name()).append(" ");
		return builder.append(generateWhere(filterString,where)).toString();
	}


}

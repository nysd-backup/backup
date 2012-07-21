/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;
import javax.persistence.Table;

import client.sql.PersistenceHints;
import client.sql.orm.FixString;
import client.sql.orm.CriteriaReadQueryParameter;
import client.sql.orm.ExtractionCriteria;
import client.sql.orm.strategy.AbstractStatementBuilder;




/**
 * The builder to create the SQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLStatementBuilderImpl extends AbstractStatementBuilder{

	/**
	 * @see client.sql.orm.strategy.AbstractStatementBuilder#afterCreateSelect(java.lang.StringBuilder, client.sql.orm.CriteriaReadQueryParameter)
	 */
	@Override
	protected StringBuilder afterCreateSelect(StringBuilder query,CriteriaReadQueryParameter<?> condition){
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
	 * @see client.sql.orm.strategy.AbstractStatementBuilder#createPrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createPrefix(Class<?> entityClass) {
		StringBuilder builder = new StringBuilder("select * from ");
		return builder.append(entityClass.getAnnotation(Table.class).name()).append(" e ");	
	}
	
	/**
	 * @see client.sql.orm.strategy.AbstractStatementBuilder#createUpdatePrefix(java.lang.Class)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(Class<?> entityClass) {
		return new StringBuilder(String.format("update %s e ",entityClass.getAnnotation(Table.class).name()));
	}

	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createInsert(java.lang.Class, java.util.Collection)
	 */
	@Override
	public String createInsert(Class<?> entityClass, Map<String,Object> keys) {
		
		StringBuilder builder = new StringBuilder("insert into ");
		String tableName = entityClass.getAnnotation(Table.class).name();
		builder.append(tableName).append(" ( ");
		boolean first = true;
		for(String key : keys.keySet()){
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
		for(Map.Entry<String, Object> e : keys.entrySet()){
			Object value = e.getValue();
			if(first){
				if(value instanceof FixString){
					builder.append(value.toString());
				}else{
					builder.append(":").append(e.getKey());
				}
				first = !first;
			}else{
				if(value instanceof FixString){
					builder.append(",").append(value.toString());
				}else{
					builder.append(",:").append(e.getKey());
				}
			}
			builder.append("\n");
		}
		builder.append(" ) ");
		return builder.toString();
		
	}

	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createDelete(java.lang.Class, java.lang.String, java.util.List)
	 */
	@Override
	public String createDelete(Class<?> entityClass,List<ExtractionCriteria> where){
		StringBuilder builder = new StringBuilder("delete from ");
		builder.append(entityClass.getAnnotation(Table.class).name()).append(" ");
		return builder.append(generateWhere(where)).toString();
	}


}

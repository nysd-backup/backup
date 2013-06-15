/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.statement;

import java.util.Map;

import javax.persistence.LockModeType;
import javax.persistence.Table;

import org.coder.alpha.query.criteria.FixString;


/**
 * SQLBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLBuilder extends AbstractStatementBuilder{
	
	/**
	 * Constructor
	 */
	protected SQLBuilder(){
		
	}
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withLock(javax.persistence.LockModeType, long)
	 */
	@Override
	public StatementBuilder withLock(LockModeType lock,long timeout){
		if(LockModeType.PESSIMISTIC_READ == lock){
			query.append("FOR UPDATE ");
			if(timeout > 0){
				query.append("TIMEOUT ").append(timeout);
			}else{
				query.append(" NOWAIT ");
			}
		}
		return this;
	}
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withSelect(Class<?> entityClass) {
		String.format("select * from %s e",entityClass.getAnnotation(Table.class).name());
		query.append(String.format("select * from %s e",entityClass.getAnnotation(Table.class).name()));
		return this;
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withDelete(Class<?> entityClass) {
		query.append(String.format("delete from %s e ",entityClass.getAnnotation(Table.class).name()));
		return this;
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withUpdate(Class<?> entityClass) {
		query.append(String.format("update %s e ",entityClass.getAnnotation(Table.class).name()));
		return this;
	}	

	/**
	 * @see org.coder.alpha.query.criteria.statement.SQLBuilder#createInsert(java.lang.Class, java.util.Collection)
	 */
	@Override
	public StatementBuilder withInsert(Class<?> entityClass, Map<String,Object> keys) {
		
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
		query.append(builder.toString());
		return this;			
	}

}


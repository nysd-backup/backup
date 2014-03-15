/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.statement;

import java.util.HashMap;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.ListHolder;
import org.coder.gear.query.criteria.SortKey;


/**
 * JPQLBuilder.
 *
 * @author yoshida-n
 * @version	1.0
 */

public class JPQLBuilder implements StatementBuilder{
	
	/** sort key */
	private ListHolder<SortKey> orderby = new ListHolder<SortKey>();
	
	/** set */
	private Map<String,Object> set = new HashMap<String,Object>();
	
	/** where */
	private ListHolder<Criteria> wheres = new ListHolder<Criteria>();
	
	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withSet(java.util.Map)
	 */
	@Override
	public StatementBuilder withSet(Map<String,Object> set){
		this.set = set;
		return this;
	}

	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withOrderBy(java.util.List)
	 */
	@Override
	public StatementBuilder withOrderBy(ListHolder<SortKey> orderby ){	
		this.orderby = orderby;
		return this;
	}		
	
	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withWhere(java.util.List)
	 */
	@Override
	public StatementBuilder withWhere(ListHolder<Criteria> wheres){	
		this.wheres = wheres;
		return this;
	}
	
	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public String buildSelect(Class<?> entityClass) {
		return String.format("select e from %s e %s %s ",
				entityClass.getSimpleName(),buildWhere(),buildOrderBy());		
	}	
	
	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public String buildDelete(Class<?> entityClass) {
		return String.format("delete from %s e %s ",entityClass.getSimpleName(),buildWhere());
	}	
	
	/**
	 * @see org.coder.gear.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public String buildUpdate(Class<?> entityClass) {
		return String.format("update %s e set %s %s ",entityClass.getSimpleName(),buildSet(),buildWhere());
	}	

	/**
	 * @return where
	 */
	protected String buildWhere() {
		StringBuilder query = new StringBuilder();
		StringBuilder builder = new StringBuilder();			
		wheres.forEach(e -> builder.append(" where")
					,e -> builder.append(" and ")
					,(e,i) -> builder.append(e.getExpression(i)));			
		query.append(builder.toString());
		return query.toString();
	}
	
	
	/**
	 * @return set
	 */
	protected String buildSet(){
		StringBuilder query = new StringBuilder();
		if( set == null || set.isEmpty()){
			throw new IllegalArgumentException("updating value is required");
		}
		StringBuilder builder = new StringBuilder();
		boolean first=true;
		for(Map.Entry<String, Object> e :set.entrySet()){	
			if( first ){			
				builder.append(" e.");
				first = false;
			}else{				
				builder.append(" , e.");
			}
			builder.append(e.getKey()).append(" = :").append(e.getKey());			
		}
		query.append(builder.toString());
		return query.toString();
	}
	

	/**
	 * @return order by
	 */
	protected String buildOrderBy(){
		StringBuilder query = new StringBuilder();
		StringBuilder builder = new StringBuilder();
		orderby.forEach(e-> builder.append(" order by ")
				, e -> builder.append(" ,")
				, (e,i) -> builder.append(String.format(" e.%s %s ",e.getColumn(),e.isAscending()? " asc " : " desc"))
				);
		query.append(builder.toString());
		return query.toString();
	}
}
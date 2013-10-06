/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.criteria.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.FixString;
import org.coder.alpha.query.criteria.SortKey;


/**
 * JPQLBuilder.
 *
 * @author yoshida-n
 * @version	1.0
 */

public class JPQLBuilder implements StatementBuilder{
	
	/** sort key */
	private List<SortKey> orderby = new ArrayList<SortKey>();
	
	/** set */
	private Map<String,Object> set = new HashMap<String,Object>();
	
	/** where */
	private List<Criteria> wheres = new ArrayList<Criteria>();
	
	/**
	 * Constructor
	 */
	protected JPQLBuilder(){
		
	}
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSet(java.util.Map)
	 */
	@Override
	public StatementBuilder withSet(Map<String,Object> set){
		this.set = set;
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withOrderBy(java.util.List)
	 */
	@Override
	public StatementBuilder withOrderBy(List<SortKey> orderby ){	
		this.orderby = orderby;
		return this;
	}		
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withWhere(java.util.List)
	 */
	@Override
	public StatementBuilder withWhere(List<Criteria> wheres){	
		this.wheres = wheres;
		return this;
	}
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public String buildSelect(Class<?> entityClass) {
		return String.format("select e from %s e %s %s ",
				entityClass.getSimpleName(),buildWhere(),buildOrderBy());		
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public String buildDelete(Class<?> entityClass) {
		return String.format("delete from %s e %s ",entityClass.getSimpleName(),buildWhere());
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
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
		if( wheres != null && !wheres.isEmpty()){
			StringBuilder builder = new StringBuilder();			
			boolean first=true;
			for(Criteria where :wheres){			
				if( first ){
					builder.append("\n where ");
					first = false;
				}else {
					builder.append("\n and ");
				}
				builder.append(where.getExpression("e"));
			}
			query.append(builder.toString());
		}
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
			Object value = e.getValue();
			if( first ){			
				builder.append("\n e.");
				first = false;
			}else{				
				builder.append("\n , e.");
			}
			if( value instanceof FixString){
				builder.append(e.getKey()).append(" = ").append(value.toString());
			}else{
				builder.append(e.getKey()).append(" = :").append(e.getKey());
			}
		}
		query.append(builder.toString());
		return query.toString();
	}
	

	/**
	 * @return order by
	 */
	protected String buildOrderBy(){
		StringBuilder query = new StringBuilder();
		if(orderby != null && !orderby.isEmpty()){
			StringBuilder builder = new StringBuilder();
			boolean first=true;		
			for(SortKey sort :orderby){				
				if( first ){
					builder.append("\n order by ");
					first = false;
				}else {
					builder.append("\n , ");
				}
				String colName = sort.getColumn();
				String ascending = sort.isAscending()?" asc ":" desc ";			
				builder.append(String.format(" e.%s %s ",colName,ascending));
			}
			query.append(builder.toString());
		}
		return query.toString();
	}
}
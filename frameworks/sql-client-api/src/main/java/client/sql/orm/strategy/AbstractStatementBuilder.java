/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.util.List;
import java.util.Map;

import client.sql.orm.CriteriaReadQueryParameter;
import client.sql.orm.ExtractionCriteria;
import client.sql.orm.FixString;
import client.sql.orm.SortKey;




/**
 * The statement builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractStatementBuilder implements SQLStatementBuilder{
	
	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createSelect(client.sql.orm.CriteriaReadQueryParameter)
	 */
	@Override
	public String createSelect(CriteriaReadQueryParameter<?> condition){
		StringBuilder builder = createPrefix(condition.getEntityClass());		
		builder.append(generateWhere(condition.getConditions())).append(generateOrderBy(condition.getSortKeys()));
		return createSuffix(builder,condition).toString();
	}
	
	/**
	 * Process after creating select statement.
	 * @param condition the condition
	 * @return query;
	 */
	protected abstract StringBuilder createSuffix(StringBuilder query, CriteriaReadQueryParameter<?> condition);
	
	/**
	 * @see client.sql.orm.strategy.SQLStatementBuilder#createUpdate(java.lang.Class, java.lang.String, java.lang.String, java.util.List, java.util.Collection)
	 */
	@Override
	public String createUpdate(Class<?> entityClass,List<ExtractionCriteria<?>> where, Map<String,Object> set) {
		StringBuilder builder = createUpdatePrefix(entityClass);
		builder.append(generateSet(set));
		builder.append(generateWhere(where));
		return builder.toString();
	}
	
	/**
	 * Creates the prefix.
	 * 
	 * @param condition　the codition
	 * @return the prefix
	 */
	protected abstract StringBuilder createPrefix(Class<?> entityClass);
	
	/**
	 * Creates the prefix.
	 * 
	 * @param condition　the codition
	 * @return the prefix
	 */
	protected abstract StringBuilder createUpdatePrefix(Class<?> entityClass);
	
	/**
	 * @param condition the condition
	 * @return the statement
	 */
	protected String generateSet(Map<String,Object> set){
		if( set == null || set.isEmpty()){
			throw new IllegalArgumentException("updating value is required");
		}
		StringBuilder builder = new StringBuilder();
		boolean first=true;
		for(Map.Entry<String, Object> e :set.entrySet()){	
			Object value = e.getValue();
			if( first ){			
				builder.append("\n set e.");
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
		return builder.toString();
	}
	
	/**
	 * Creates the WHERE statement.
	 * 
	 * @param condition　the condition
	 * @return the statement
	 */
	protected String generateWhere(List<ExtractionCriteria<?>> wheres){		
		if( wheres == null || wheres.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		for(ExtractionCriteria<?> where :wheres){			
			if( first ){
				builder.append("\n where ");
				first = false;
			}else {
				builder.append("\n and ");
			}
			builder.append(where.getExpression("e"));
		}
		return builder.toString();
	}

	/**
	 * Creates the ORDER BY statement.
	 * 
	 * @param condition the condition
	 * @return the statement
	 */
	protected String generateOrderBy(List<SortKey> orderby ){	
		if( orderby == null || orderby.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		
		for(SortKey sort :orderby){				
			if( first ){
				builder.append("\n order by ");
			}else {
				builder.append("\n , ");
			}
			String colName = sort.getColumn();
			String ascending = sort.isAscending()?" asc ":" desc ";			
			builder.append(String.format(" e.%s %s ",colName,ascending));
		}
		return builder.toString();
	}
}

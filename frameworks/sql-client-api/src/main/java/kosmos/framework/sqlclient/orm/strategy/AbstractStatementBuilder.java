/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm.strategy;

import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.orm.FixString;
import kosmos.framework.sqlclient.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.orm.SortKey;
import kosmos.framework.sqlclient.orm.WhereCondition;
import kosmos.framework.sqlclient.orm.WhereOperand;


/**
 * The statement builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractStatementBuilder implements SQLStatementBuilder{
	
	/**
	 * @see kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder#createSelect(kosmos.framework.sqlclient.orm.OrmQueryParameter)
	 */
	@Override
	public String createSelect(OrmQueryParameter<?> condition){
		StringBuilder builder = createPrefix(condition.getEntityClass());		
		builder.append(generateWhere(condition.getConditions())).append(generateOrderBy(condition.getSortKeys()));
		return afterCreateSelect(builder,condition).toString();
	}
	
	/**
	 * Process after creating select statement.
	 * @param condition the condition
	 * @return query;
	 */
	protected StringBuilder afterCreateSelect(StringBuilder query, OrmQueryParameter<?> condition){
		return query;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder#createUpdate(java.lang.Class, java.lang.String, java.lang.String, java.util.List, java.util.Collection)
	 */
	@Override
	public String createUpdate(Class<?> entityClass,List<WhereCondition> where, Map<String,Object> set) {
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
			throw new IllegalArgumentException("set parameter is required");
		}
		StringBuilder builder = new StringBuilder();
		boolean first=true;
		for(Map.Entry<String, Object> e :set.entrySet()){	
			Object value = e.getValue();
			if( first ){			
				if( value instanceof FixString){
					builder.append("\n set e.").append(e.getKey()).append(" = ").append(value.toString());
				}else{
					builder.append("\n set e.").append(e.getKey()).append(" = :").append(e.getKey());
				}
				first = false;
			}else{
				if( value instanceof FixString){
					builder.append("\n , e.").append(e.getKey()).append(" = ").append(value.toString());
				}else{
					builder.append("\n , e.").append(e.getKey()).append(" = :").append(e.getKey());
				}			
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
	protected String generateWhere(List<WhereCondition> wheres){		
		if( wheres == null || wheres.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		for(WhereCondition where :wheres){	
			WhereOperand operand = where.getOperand();
			if( first ){
				builder.append("\n where ");
				first = false;
			}else {
				builder.append("\n and ");
			}

			// BETWEEN
			if(operand == WhereOperand.Between) {
				Object toValue = where.getToValue();
				String to = null;
				if(toValue instanceof FixString){
					to = toValue.toString();
				}else{
					to = String.format(":%s%d_to ",where.getColName(),where.getBindCount());	
				}
				Object fromValue = where.getValue();
				String from = null;
				if(fromValue instanceof FixString){
					from = fromValue.toString();	
				}else{
					from = String.format(":%s%d",where.getColName(),where.getBindCount());					
				}				
				builder.append(String.format("e.%s%s %s and %s ",where.getColName(),operand.getOperand(),from,to));
				
			// IN句	
			}else if (operand == WhereOperand.IN){
				StringBuilder in = new StringBuilder();
				Object value = where.getValue();
				if(value instanceof List){
					List<?> val = List.class.cast(where.getValue());
					for(int i = 0 ; i <val.size(); i++){
						in.append(":").append(where.getColName()).append("_").append(where.getBindCount()).append("_").append(i).append(",");
					}
					if(in.length() > 0){
						in = new StringBuilder(in.substring(0,in.length()-1));
					}				
				}else if( value instanceof FixString){
					in.append(value.toString());
				}else{
					in.append(":").append(where.getColName());
				}
				
				builder.append(String.format("e.%s IN(%s) ",where.getColName(),in));
			}else {
				Object value = where.getValue();
				if(value instanceof FixString){
					builder.append(String.format("e.%s%s%s",where.getColName(),operand.getOperand(),value.toString()));
				}else{
					builder.append(String.format("e.%s%s:%s%d ",where.getColName(),operand.getOperand(),where.getColName(),where.getBindCount()));								
				}
			}
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

	/**
	 * Set the parameter to delegate.
	 * 
	 * @param condition the condition
	 * @param delegate the delegate
	 */
	public void setConditionParameters(List<WhereCondition> baseCondition ,Bindable delegate){
	
		for(WhereCondition cond : baseCondition){
			if(WhereOperand.IN == cond.getOperand()){
				Object value = cond.getValue();
				if( value instanceof List ){
					List<?> val = List.class.cast(value);
					int cnt = -1;
					for(Object v : val){
						cnt++;
						delegate.setParameter(String.format("%s_%d_%d", cond.getColName(),cond.getBindCount(),cnt),v);
					}
				}else if( value instanceof FixString){
					continue;
				}else {
					delegate.setParameter(String.format("%s_%d", cond.getColName(),cond.getBindCount()),value);
				}
			}else if( WhereOperand.Between == cond.getOperand()){
				Object toValue = cond.getToValue();
				Object fromValue = cond.getValue();
				if(!(toValue instanceof FixString)){
					delegate.setParameter(String.format("%s%d", cond.getColName(), cond.getBindCount()),cond.getValue());
				}
				if(!(fromValue instanceof FixString)){
					delegate.setParameter(String.format( "%s%d_to",cond.getColName(),cond.getBindCount()), cond.getToValue());	
				}				
				
			}else{
				Object value = cond.getValue();
				if(!(value instanceof FixString)){
					delegate.setParameter(String.format("%s%d", cond.getColName(), cond.getBindCount()),cond.getValue());
				}
			}			
		}
	}
	
}

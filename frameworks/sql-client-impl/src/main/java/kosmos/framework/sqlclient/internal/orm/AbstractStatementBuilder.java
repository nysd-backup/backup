/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm;

import java.util.Collection;
import java.util.List;

import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.SortKey;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;


/**
 * The statement builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractStatementBuilder implements SQLStatementBuilder{
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createSelect(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public String createSelect(OrmQueryParameter<?> condition){
		StringBuilder builder = createPrefix(condition.getEntityClass());		
		builder.append(generateWhere(condition.getFilterString(),condition.getConditions())).append(generateOrderBy(condition.getOrderString(),condition.getSortKeys()));
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
	 * @see kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder#createUpdate(java.lang.Class, java.lang.String, java.lang.String, java.util.List, java.util.Collection)
	 */
	@Override
	public String createUpdate(Class<?> entityClass,String filterString,List<WhereCondition> where, Collection<String> set) {
		StringBuilder builder = createUpdatePrefix(entityClass);
		builder.append(generateSet(set));
		builder.append(generateWhere(filterString,where));
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
	protected String generateSet(Collection<String> set){
		if( set == null || set.isEmpty()){
			throw new IllegalArgumentException("set parameter is required");
		}
		StringBuilder builder = new StringBuilder();
		boolean first=true;
		for(String name :set){	
			if( first ){
				builder.append("\n set e.").append(name).append(" = :").append(name);
				first = false;
			}else{
				builder.append("\n , e.").append(name).append(" = :").append(name);
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
	protected String generateWhere(String filterString, List<WhereCondition> wheres){
		if(filterString != null){
			return "\n where " + filterString;
		}
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
				String from = String.format("e.%s%s:%s%d", where.getColName(),	operand.getOperand(),where.getColName(),where.getBindCount());
				String to = String.format(":%s%d_to ",where.getColName(),where.getBindCount());
				builder.append(String.format(" %s and %s ",from,to));
				
			// IN句	
			}else if (operand == WhereOperand.IN){
				StringBuilder in = new StringBuilder();
				List<?> val = List.class.cast(where.getValue());
				for(int i = 0 ; i <val.size(); i++){
					in.append(":").append(where.getColName()).append("_").append(where.getBindCount()).append("_").append(i).append(",");
				}
				if(in.length() > 0){
					in = new StringBuilder(in.substring(0,in.length()-1));
				}
				builder.append(String.format("e.%s IN(%s) ",where.getColName(),in));								
			}else {
				builder.append(String.format("e.%s%s:%s%d ",where.getColName(),operand.getOperand(),where.getColName(),where.getBindCount()));								
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
	protected String generateOrderBy(String orderString, List<SortKey> orderby ){
		if(orderString != null){
			return "\n order by " + orderString;
		}
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
	public void setConditionParameters(String filterString ,Object[] easyParams, List<WhereCondition> baseCondition ,Bindable delegate){
		//簡易フィルターが設定されている場合、実行時に設定されたパラメータを使用する
		if(filterString != null){
			if(easyParams != null){
				for(int i = 0; i < easyParams.length; i++){
					delegate.setParameter(String.format("p%d", i+1),easyParams[i]);
				}
			}
			return ;
		}

		for(WhereCondition cond : baseCondition){
			if(WhereOperand.IN == cond.getOperand()){
				List<?> val = List.class.cast(cond.getValue());
				int cnt = -1;
				for(Object v : val){
					cnt++;
					delegate.setParameter(String.format("%s_%d_%d", cond.getColName(),cond.getBindCount(),cnt),v);
				}
			}else{	
				delegate.setParameter(String.format("%s%d", cond.getColName(), cond.getBindCount()),cond.getValue());
				if( WhereOperand.Between == cond.getOperand()){
					delegate.setParameter(String.format( "%s%d_to",cond.getColName(),cond.getBindCount()), cond.getToValue());
				}
			}
		}
	}
	
}

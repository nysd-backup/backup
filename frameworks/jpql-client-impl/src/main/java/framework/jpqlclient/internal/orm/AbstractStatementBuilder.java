/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm;

import java.util.List;

import framework.sqlclient.api.orm.OrmCondition;
import framework.sqlclient.api.orm.SortKey;
import framework.sqlclient.api.orm.WhereCondition;
import framework.sqlclient.api.orm.WhereOperand;

/**
 * The statement builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractStatementBuilder {
	
	/**
	 * Creates the SELECT statement.
	 * 
	 * @param condition the condition
	 * @return the statement
	 */
	public String createSelect(OrmCondition<?> condition){
		StringBuilder builder = createPrefix(condition);		
		builder.append(generateWhere(condition)).append(generateOrderBy(condition));
		return builder.toString();
	}
	
	/**
	 * Creates the prefix.
	 * 
	 * @param condition　the codition
	 * @return the prefix
	 */
	protected abstract StringBuilder createPrefix(OrmCondition<?> condition);
	
	/**
	 * Creates the WHERE statement.
	 * 
	 * @param condition　the condition
	 * @return the statement
	 */
	protected String generateWhere(OrmCondition<?> condition){
		if(condition.getFilterString() != null){
			return " where " + condition.getFilterString();
		}
		List<WhereCondition> wheres = condition.getConditions();
		if( wheres == null || wheres.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		for(WhereCondition where :wheres){	
			WhereOperand operand = where.getOperand();
			if( first ){
				builder.append(" where ");
				first = false;
			}else {
				builder.append("and ");
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
	protected String generateOrderBy(OrmCondition<?> condition){
		if(condition.getOrderString() != null){
			return " order by " +condition.getOrderString();
		}
		List<SortKey> orderby = condition.getSortKeys();
		if( orderby == null || orderby.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		
		for(SortKey sort :orderby){				
			if( first ){
				builder.append(" order by ");
			}else {
				builder.append(" , ");
			}
			String colName = sort.getColumn();
			String ascending = sort.isAscending()?" asc ":" desc ";			
			builder.append(String.format(" e.%s %s ",colName,ascending));
		}
		return builder.toString();
	}


}

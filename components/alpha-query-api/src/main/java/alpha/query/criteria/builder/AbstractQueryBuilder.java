/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.builder;

import java.util.List;
import java.util.Map;

import alpha.query.criteria.Criteria;
import alpha.query.criteria.FixString;
import alpha.query.criteria.SortKey;


/**
 * The statement builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractQueryBuilder implements QueryBuilder{
	
	protected StringBuilder query = new StringBuilder();
	
	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withSet(java.util.Map)
	 */
	@Override
	public QueryBuilder withSet(Map<String,Object> set){
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
		query.append(builder.toString());
		return this;
	}

	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withOrderBy(java.util.List)
	 */
	@Override
	public QueryBuilder withOrderBy(List<SortKey> orderby ){	
		if(orderby == null || orderby.isEmpty()){
			return this;
		}
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
		return this;
	}
	
	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withWhere(java.util.List)
	 */
	@Override
	public QueryBuilder withWhere(List<Criteria<?>> wheres){		
		if( wheres == null || wheres.isEmpty()){
			return this;
		}
		StringBuilder builder = new StringBuilder();
		
		boolean first=true;
		for(Criteria<?> where :wheres){			
			if( first ){
				builder.append("\n where ");
				first = false;
			}else {
				builder.append("\n and ");
			}
			builder.append(where.getExpression("e"));
		}
		query.append(builder.toString());
		return this;
	}

	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#build()
	 */
	@Override
	public String build() {
		return query.toString();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return build();
	}
}

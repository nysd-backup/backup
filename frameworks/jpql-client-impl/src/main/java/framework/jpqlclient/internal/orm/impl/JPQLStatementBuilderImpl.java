/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm.impl;

import java.util.Collection;

import framework.jpqlclient.internal.orm.AbstractStatementBuilder;
import framework.jpqlclient.internal.orm.JPQLStatementBuilder;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * JPQLのSQL文を作成する.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
class JPQLStatementBuilderImpl extends AbstractStatementBuilder implements JPQLStatementBuilder{

	/**
	 * @see framework.jpqlclient.internal.orm.AbstractStatementBuilder#createPrefix(framework.api.query.OrmCondition)
	 */
	@Override
	protected StringBuilder createPrefix(OrmCondition<?> condition) {
		StringBuilder builder = new StringBuilder("select e from ");
		builder.append(condition.getEntityClass().getSimpleName()).append(" e ");
		return builder;
	}

	/**
	 * @see framework.jpqlclient.internal.orm.JPQLStatementBuilder#createDelete(framework.api.query.OrmCondition)
	 */
	@Override
	public String createDelete(OrmCondition<?> condition) {
		StringBuilder builder = new StringBuilder("delete e from ");
		builder.append(condition.getEntityClass().getSimpleName()).append(" e ");
		builder.append(generateWhere(condition));
		return builder.toString();
	}
	
	/**
	 * @see framework.jpqlclient.internal.orm.JPQLStatementBuilder#createUpdate(framework.api.entity.AbstractEntity, framework.api.entity.AbstractEntity)
	 */
	@Override
	public String createUpdate(OrmCondition<?> condition, Collection<String> set) {
		StringBuilder builder = new StringBuilder(String.format("update %s e ",condition.getEntityClass().getSimpleName()));
		builder.append(generateSet(set));
		builder.append(generateWhere(condition));
		return builder.toString();
	}
	
	/**
	 * @param condition　更新条件
	 * @return SET句
	 */
	private String generateSet(Collection<String> set){
		if( set == null || set.isEmpty()){
			throw new IllegalArgumentException("set parameter is required");
		}
		StringBuilder builder = new StringBuilder();
		boolean first=true;
		for(String name :set){	
			if( first ){
				builder.append("set e.").append(name).append(" = :").append(name);
				first = false;
			}else{
				builder.append(", e.").append(name).append(" = :").append(name);
			}
		}
		return builder.toString();
	}

}

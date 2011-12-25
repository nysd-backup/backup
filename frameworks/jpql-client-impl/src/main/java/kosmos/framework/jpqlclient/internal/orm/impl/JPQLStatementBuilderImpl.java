/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import kosmos.framework.jpqlclient.internal.orm.JPQLStatementBuilder;
import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder;


/**
 * The builder to create the JPQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
class JPQLStatementBuilderImpl extends AbstractStatementBuilder implements JPQLStatementBuilder{

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createPrefix(framework.api.query.OrmCondition)
	 */
	@Override
	protected StringBuilder createPrefix(OrmQueryContext<?> condition) {
		return new StringBuilder(String.format("select e from %s e",condition.getEntityClass().getSimpleName()));
	}

	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.JPQLStatementBuilder#createDelete(framework.api.query.OrmCondition)
	 */
	@Override
	public String createDelete(OrmContext<?> condition) {
		StringBuilder builder = new StringBuilder("delete e from ");
		builder.append(condition.getEntityClass().getSimpleName()).append(" e ");
		builder.append(generateWhere(condition));
		return builder.toString();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.AbstractStatementBuilder#createUpdatePrefix(kosmos.framework.sqlclient.api.orm.OrmContext)
	 */
	@Override
	protected StringBuilder createUpdatePrefix(OrmContext<?> condition) {
		return new StringBuilder(String.format("update %s e ",condition.getEntityClass().getSimpleName()));
	}
	

}

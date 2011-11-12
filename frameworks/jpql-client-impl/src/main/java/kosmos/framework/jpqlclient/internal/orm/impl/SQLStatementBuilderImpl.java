/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import javax.persistence.Table;

import kosmos.framework.jpqlclient.internal.orm.AbstractStatementBuilder;
import kosmos.framework.jpqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlclient.api.orm.OrmCondition;


/**
 * The builder to create the SQL statement.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
class SQLStatementBuilderImpl extends AbstractStatementBuilder implements SQLStatementBuilder{

	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.AbstractStatementBuilder#createPrefix(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	protected StringBuilder createPrefix(OrmCondition<?> condition) {
		StringBuilder builder = new StringBuilder("select * from ");
		return builder.append(condition.getEntityClass().getAnnotation(Table.class).name()).append(" e ");
		
	}
}

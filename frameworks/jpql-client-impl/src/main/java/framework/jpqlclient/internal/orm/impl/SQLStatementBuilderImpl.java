/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.orm.impl;

import javax.persistence.Table;

import framework.jpqlclient.internal.orm.AbstractStatementBuilder;
import framework.jpqlclient.internal.orm.SQLStatementBuilder;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * JPQLのSQL文を作成する.
 *
 * @author	yoshida-n
 * @version	created.
 */
class SQLStatementBuilderImpl extends AbstractStatementBuilder implements SQLStatementBuilder{

	/**
	 * @see framework.jpqlclient.internal.orm.AbstractStatementBuilder#createPrefix(framework.api.query.OrmCondition)
	 */
	@Override
	protected StringBuilder createPrefix(OrmCondition<?> condition) {
		StringBuilder builder = new StringBuilder("select * from ");
		return builder.append(condition.getEntityClass().getAnnotation(Table.class).name()).append(" e ");
		
	}
}

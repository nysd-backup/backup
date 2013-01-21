/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.strategy.impl;

import alpha.query.criteria.strategy.AbstractDataMapper;
import alpha.query.criteria.strategy.QueryStatementBuilder;


/**
 * The JPQLDataMapperImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLDataMapperImpl extends AbstractDataMapper {

	/**
	 * @see alpha.query.criteria.strategy.AbstractDataMapper#createBuilder()
	 */
	@Override
	protected QueryStatementBuilder createBuilder() {
		return new SQLStatementBuilder();
	}
	
}
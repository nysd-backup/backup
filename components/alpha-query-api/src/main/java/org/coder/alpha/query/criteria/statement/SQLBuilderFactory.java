/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.statement;



/**
 * The JPQLBuilderFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLBuilderFactory implements StatementBuilderFactory {

	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilderFactory#createBuilder()
	 */
	@Override
	public StatementBuilder createBuilder() {
		return new SQLBuilder();
	}
	
}
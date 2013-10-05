/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.criteria.statement;



/**
 * The JPQLBuilderFactory.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class JPQLBuilderFactory implements StatementBuilderFactory {

	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilderFactory#createBuilder()
	 */
	@Override
	public StatementBuilder createBuilder() {
		return new JPQLBuilder();
	}
	

}
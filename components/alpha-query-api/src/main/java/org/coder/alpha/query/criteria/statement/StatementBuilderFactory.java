/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.statement;



/**
 * Internal Query Object.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface StatementBuilderFactory {

	/**
	 *　Updates the table.
	 *
	 * @param parameter the parameter
	 * @param set the updating target
	 * @return the updated count
	 */
	public StatementBuilder createBuilder();

}

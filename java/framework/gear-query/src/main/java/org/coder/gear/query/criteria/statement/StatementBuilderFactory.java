/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.statement;



/**
 * Internal Query Object.
 *
 * @author yoshida-n
 * @version	1.0
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

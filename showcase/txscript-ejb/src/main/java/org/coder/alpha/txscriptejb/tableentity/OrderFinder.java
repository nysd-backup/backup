/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.tableentity;



/**
 * OrderFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderFinder extends TableEntityFinder<Order> {

	/**
	 * @see org.coder.alpha.txscriptejb.tableentity.TableEntityFinder#getEntityClass()
	 */
	@Override
	protected Class<Order> getEntityClass() {
		return Order.class;
	}

	
}

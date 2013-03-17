/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;


/**
 * Tests if the current transaction must be rolled back.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RollbackTrigger {
	
	/**
	 * @return the source object
	 */
	public Object getSource();
	
	/**
	 * @return true: rollback required
	 */
	public boolean isRollbackRequired();
	
}

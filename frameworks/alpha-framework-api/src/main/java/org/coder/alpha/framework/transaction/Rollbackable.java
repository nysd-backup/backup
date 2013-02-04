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
public interface Rollbackable {

	/**
	 * Tests if the current transaction must be rolled back.
	 * 
	 * @param value object
	 * @return true:should rollback
	 */
	public boolean isRollbackRequired();
	
	
	/**
	 * Gets the source object
	 * 
	 * @return source
	 */
	public Object getSource();
}

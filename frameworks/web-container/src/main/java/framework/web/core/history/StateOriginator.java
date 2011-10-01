/**
 * Copyright 2011 the original author
 */
package framework.web.core.history;

/**
 * Saves and restores the state.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StateOriginator {

	/**
	 * Creates the snapshot of the current state.
	 * 
	 * @return the current state
	 */	
	public PageMemento save();
	
	/**
	 * Restores the state from the snapshot.
	 * 
	 * @param mement the state
	 */
	public void restore(PageMemento mement);
}

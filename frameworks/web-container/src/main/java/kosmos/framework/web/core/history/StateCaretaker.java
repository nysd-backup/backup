package kosmos.framework.web.core.history;

import java.util.Iterator;

/**
 * Holds the state.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StateCaretaker {

	/**
	 * @param viewId the viewId
	 * @return true:exists
	 */
	public abstract boolean exists(String viewId);

	/**
	 * @param memento the memento to be added
	 */
	public abstract void push( PageMemento memento);

	/**
	 * Retrieves, but does not remove, the head (first element) of this list.
	 * 
	 * @return the history that is added last.
	 */
	public abstract PageMemento peek();

    /**
     * Pops an element from the stack represented by this list.  In other
     * words, removes and returns the first element of this list.
     *
     * @return the history that is added last.
     */
	public abstract PageMemento pop();

	/**
	 * @return the iterator
	 */
	public abstract Iterator<PageMemento> iterator();

	/**
	 * @return the count of state
	 */
	public abstract int size();

	/**
	 * Clears the state.
	 */
	public abstract void clear();

}

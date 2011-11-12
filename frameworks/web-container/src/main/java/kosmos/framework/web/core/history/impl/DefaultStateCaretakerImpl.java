/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.history.impl;

import java.util.Iterator;
import java.util.LinkedList;

import kosmos.framework.web.core.history.PageMemento;
import kosmos.framework.web.core.history.StateCaretaker;

/**
 * Holds the state.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStateCaretakerImpl implements StateCaretaker {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** the holder */
	private final LinkedList<PageMemento> history = new LinkedList<PageMemento>();


	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String viewId) {

		Iterator<PageMemento> itr = this.iterator();
		while (itr.hasNext()) {
			PageMemento h = itr.next();
			if (h.getViewId().equals(viewId)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#push(kosmos.framework.web.core.history.PageMemento)
	 */
	@Override
	public void push(PageMemento navigationHistory) {
		history.add(navigationHistory);
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#peek()
	 */
	@Override
	public PageMemento peek() {		
		if (size() == 0) {
			return null;
		}
		return history.getLast();
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#pop()
	 */
	@Override
	public PageMemento pop() {
		PageMemento last = peek();
		if (last != null) {
			// 取り出した履歴を削除
			history.removeLast();
		}

		return last;
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#iterator()
	 */	
	@Override
	public Iterator<PageMemento> iterator() {
		return history.iterator();
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#size()
	 */
	@Override
	public int size() {
		return history.size();
	}

	/**
	 * @see kosmos.framework.web.core.history.StateCaretaker#clear()
	 */
	@Override
	public void clear() {
		history.clear();
	}


}

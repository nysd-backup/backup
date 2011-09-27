/**
 * Copyright 2011 the original author
 */
package framework.web.core.history.impl;

import java.util.Iterator;
import java.util.LinkedList;
import framework.web.core.history.PageMement;
import framework.web.core.history.StateCaretaker;

/**
 * 画面状態履歴を保持する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStateCaretakerImpl implements StateCaretaker {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 履歴を保持するLinkedList */
	private final LinkedList<PageMement> history = new LinkedList<PageMement>();


	/**
	 * @see framework.web.core.history.StateCaretaker#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String viewId) {

		Iterator<PageMement> itr = this.iterator();
		while (itr.hasNext()) {
			PageMement h = itr.next();
			if (h.getViewId().equals(viewId)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#push(framework.web.core.history.PageMement)
	 */
	@Override
	public void push(PageMement navigationHistory) {
		history.add(navigationHistory);
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#peek()
	 */
	@Override
	public PageMement peek() {		
		if (size() == 0) {
			return null;
		}
		return history.getLast();
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#pop()
	 */
	@Override
	public PageMement pop() {
		PageMement last = peek();
		if (last != null) {
			// 取り出した履歴を削除
			history.removeLast();
		}

		return last;
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#iterator()
	 */	
	@Override
	public Iterator<PageMement> iterator() {
		return history.iterator();
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#size()
	 */
	@Override
	public int size() {
		return history.size();
	}

	/**
	 * @see framework.web.core.history.StateCaretaker#clear()
	 */
	@Override
	public void clear() {
		history.clear();
	}


}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import java.util.List;

/**
 * RollbackTriggerListener.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RollbackTriggerListener {

	/**
	 * Handle before append
	 * @param trigger the trigger
	 * @return
	 */
	public boolean onBeforeAppend(List<Object> trigger);
}

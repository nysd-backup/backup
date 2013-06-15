/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * RollbackTriggers.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RollbackTriggers {
	
	/** the messageList */
	private List<Object> triggers = new ArrayList<Object>();
	
	/** the listener */
	private RollbackTriggerListener listener;
	
	/**
	 * @param listener the listener 
	 */
	public void setEventListener(RollbackTriggerListener listener){
		this.listener = listener;
	}
	
	/**
	 * @return the messageList
	 */
	public List<Object> getTriggers() {
		return this.triggers;
	}
	
	/**
	 * @param message the target message
	 */
	public boolean append(RollbackTrigger trigger){	
		boolean appendable = listener == null ? true: listener.onBeforeAppend(triggers);
		if(appendable){		
			for(Object o : triggers){
				if(o.equals(trigger.getSource())){
					return false;
				}
			}			
			triggers.add(trigger.getSource());
			return true;
		}
		return appendable;
	}

}

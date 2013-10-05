/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.result;

import java.util.ArrayList;

/**
 * The query result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TotalList<E> extends ArrayList<E>{

	private static final long serialVersionUID = 5672526496382132804L;

	/** if true hit count is more than max size */
	private boolean limited = false;
	
	/** the hit count */
	private int hitCount = 0;
	
	/**
	 * Increment hit count.
	 */
	public void setHitCount(int hitCount){
		this.hitCount = hitCount;
	}
	
	/**
	 * Set the limit.
	 */
	public void limited(){
		limited = true;
	}
	
	/**
	 * @return f true hit count is more than max size
	 */
	public boolean isLimited(){
		return this.limited;
	}

	/**
	 * @return the hit count
	 */
	public int getHitCount(){
		return this.hitCount;
	}
}

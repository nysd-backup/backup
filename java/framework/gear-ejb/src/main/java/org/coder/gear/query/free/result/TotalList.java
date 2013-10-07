/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.result;

import java.util.ArrayList;

/**
 * The query result.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class TotalList<E> extends ArrayList<E>{

	private static final long serialVersionUID = 5672526496382132804L;

	/** if true hit count is more than max size */
	private boolean exceededLimit = false;
	
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
	public void exceededLimit(){
		exceededLimit = true;
	}
	
	/**
	 * @return f true hit count is more than max size
	 */
	public boolean isExceededLimit(){
		return this.exceededLimit;
	}

	/**
	 * @return the hit count
	 */
	public int getHitCount(){
		return this.hitCount;
	}
}

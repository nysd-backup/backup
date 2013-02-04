/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import java.util.ArrayList;

/**
 * The bean that is called only in WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class HitData<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	/** if true hit count is over the limit */
	private final boolean limitedOver;
	
	/** the hit count */
	private final int hitCount;
	
	/**
	 * @param limitedOver the limitedOver to set 
	 * @param result the result to set
	 * @param hitCount the hitCount to set
	 */
	public HitData(boolean limitedOver ,int hitCount){
		this.limitedOver = limitedOver;		
		this.hitCount = hitCount;
	}
	
	/**
	 * @return the limitedOver
	 */
	public boolean isLimited(){
		return this.limitedOver;
	}
	
	/**
	 * @return the hit count
	 */
	public int getHitCount(){
		return this.hitCount;
	}

}

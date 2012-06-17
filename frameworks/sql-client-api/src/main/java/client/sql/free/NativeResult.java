/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import java.io.Serializable;
import java.util.List;

/**
 * The bean that is called only in WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class NativeResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/** if true hit count is over the limit */
	private final boolean limitedOver;
	
	/** the result */
	private final List resultList;
	
	/** the hit count */
	private final int hitCount;
	
	/**
	 * @param limitedOver the limitedOver to set 
	 * @param result the result to set
	 * @param hitCount the hitCount to set
	 */
	public NativeResult(boolean limitedOver , List result , int hitCount){
		this.limitedOver = limitedOver;
		this.resultList = result;
		this.hitCount = hitCount;
	}
	
	/**
	 * @return the limitedOver
	 */
	public boolean isLimited(){
		return this.limitedOver;
	}
	
	/**
	 * @return the result
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(){
		return (List<T>)this.resultList;
	}
	
	/**
	 * @return the hit count
	 */
	public int getHitCount(){
		return this.hitCount;
	}

}

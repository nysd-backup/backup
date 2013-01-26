/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import java.util.Iterator;
import java.util.List;


/**
 * The native query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNativeReadQuery extends AbstractReadQuery{

	/**
	 * Gets the total result.
	 * @return the result
	 */
	public HitData getTotalResult() {
		return getPersistenceGateway().getTotalResult(getParameter());
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @param callback the callback
	 * @return the hit count
	 */
	@SuppressWarnings("unchecked")
	public long getFetchResult(QueryCallback<?> callback){
		List<Object> lazyList = getFetchResult();
		Iterator<Object> iterator = lazyList.iterator();
		long count = 0;
		try{
			callback.initialize();
			while(iterator.hasNext()){	
				((QueryCallback<Object>)callback).handleRow(iterator.next(), count);
				count++;
			}
		}finally{
			try{
				lazyList.clear();
			}finally{
				callback.terminate();
			}
		}
		return count;
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @return the result holding ResultSet
	 */
	public <T> List<T> getFetchResult(){
		return getPersistenceGateway().getFetchResult(getParameter());
	}

	/**
	 * Sets the query filter
	 * @param filter the filter
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractNativeReadQuery> T setFilter(ResultSetFilter filter) {
		getParameter().setFilter(filter);
		return (T)this;
	}
	
}

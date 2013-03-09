/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria;

import java.util.concurrent.Callable;

import org.coder.alpha.query.criteria.query.CriteriaQuery;

/**
 * AsyncCriteriaQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class AsyncCriteriaQuery<V> implements Callable<V>{
	 
	private final CriteriaQuery<?,V> query;
	
	/**
	 * @param query the query to execute
	 */
	public AsyncCriteriaQuery(CriteriaQuery<?, V> query){
		this.query = query;
	}

	/**
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public V call() throws Exception {
		return query.call();
	}
}

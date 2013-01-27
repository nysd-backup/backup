/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.QueryCallback;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.gateway.PersistenceGateway;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FetchReadQuery<E> extends ReadQuery<E,List<E>>{

	/** the PersistenceGateway */
	private PersistenceGateway gateway;
	
	
	/**
	 * Constructor
	 * @param entityClass
	 * @param em
	 * @param builderFactory
	 * @param gateway
	 */
	public FetchReadQuery(Class<E> entityClass, EntityManager em,
			StatementBuilderFactory builderFactory,PersistenceGateway gateway) {
		super(entityClass, em, builderFactory);
		this.gateway = gateway;
	}

	
	/**
	 * @see org.coder.alpha.query.criteria.query.ReadQuery#doCallInternal(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	protected List<E> doCallInternal(ReadingConditions conditions) {
		return gateway.getFetchResult(conditions);
	}
	
	/**
	 * Get the result with callback.
	 * @param callback the callback
	 * @return
	 */
	public long callWithCallback(QueryCallback<E> callback){
		List<E> lazyList = call();
		Iterator<E> iterator = lazyList.iterator();
		long count = 0;
		try{
			callback.initialize();
			while(iterator.hasNext()){	
				callback.handleRow(iterator.next(), count);
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
	
}

/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.List;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.ListHolder;
import org.coder.gear.query.free.query.Conditions;

/**
 * ListReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class ListQuery<E> extends ReadQuery<List<E>>{
	
	/** the max size */
	private int maxResults = -1;
	
	/**
	 * @param maxSize the maxSize to set
	 * @return self
	 */
	public ListQuery<E> limit(int maxResults){
		this.maxResults = maxResults;
		return this;
	}
	
	/**
	 * @see org.coder.gear.query.criteria.query.ReadQuery#createConditions(java.util.List)
	 */
	@Override
	protected Conditions createConditions(ListHolder<Criteria> criterias) {
		Conditions condition = super.createConditions(criterias);
		condition.setMaxResults(maxResults);
		return condition;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.ReadQuery#doCallInternal(org.coder.gear.query.free.query.ReadingConditions)
	 */
	@Override
	protected List<E> doCallInternal(Conditions conditions) {
		return gateway.getResultList(conditions);
	}
	
}

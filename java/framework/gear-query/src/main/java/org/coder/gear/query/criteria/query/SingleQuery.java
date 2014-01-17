/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.List;

import org.coder.gear.query.free.query.Conditions;


/**
 * SingleReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class SingleQuery<E> extends ReadQuery<E>{
	

	/**
	 * @see org.coder.gear.query.criteria.query.ReadQuery#doCallInternal(org.coder.gear.query.free.query.Conditions)
	 */
	@Override
	protected E doCallInternal(Conditions conditions) {
		List<E> result = gateway.getResultList(conditions);
		return result.isEmpty() ? null : result.get(0);
	}

}

/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.ListHolder;
import org.coder.gear.query.free.query.Conditions;

/**
 * UpdateQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class DeleteQuery extends ModifyQuery{
	

	/**
	 * @see org.coder.gear.query.criteria.query.ModifyQuery#doCallInternal(org.coder.gear.query.free.query.Conditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(Conditions conditions,
			ListHolder<Criteria> criterias) {
		String sql = builder.withWhere(criterias).buildDelete(entityClass);
		conditions.setQueryId(entityClass.getSimpleName() + ".delete");
		conditions.setSql(sql);		
		return gateway.executeUpdate(conditions);
	}

}

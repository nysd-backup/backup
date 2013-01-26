/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.free.QueryFactory;

/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryFactoryFinder {

	/**
	 * @return QueryFactory
	 */
	QueryFactory createQueryFactory();
	
	/**
	 * @return CriteriaQueryFactory
	 */ 
	CriteriaQueryFactory createCriteriaQueryFactory();
}

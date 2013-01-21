/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;

import alpha.query.criteria.CriteriaQueryFactory;
import alpha.query.free.QueryFactory;

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

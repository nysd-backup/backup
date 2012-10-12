/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.query;

import alpha.sqlclient.free.QueryFactory;
import alpha.sqlclient.orm.CriteriaQueryFactory;

/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryFactoryProvider {

	/**
	 * @return QueryFactory
	 */
	QueryFactory createQueryFactory();
	
	/**
	 * @return CriteriaQueryFactory
	 */ 
	CriteriaQueryFactory createCriteriaQueryFactory();
}

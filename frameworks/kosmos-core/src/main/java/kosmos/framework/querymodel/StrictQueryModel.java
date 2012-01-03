/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import kosmos.framework.core.query.LimitedOrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;

/**
 * QueryModel for OrmQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StrictQueryModel extends QueryModel {

	/** the parameter */
	private OrmQueryParameter<Object> parameter = null;
	
	/**
	 * @param query
	 */
	public StrictQueryModel(LimitedOrmQuery<Object> query){
		parameter = query.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.querymodel.QueryModel#accept(kosmos.framework.querymodel.QueryProcessor)
	 */
	@Override
	public void accept(QueryProcessor updater){
		updater.getResultList(this);
	}

	/**
	 * @return the request
	 */
	public OrmQueryParameter<Object> getRequest() {
		return parameter;
	}

}

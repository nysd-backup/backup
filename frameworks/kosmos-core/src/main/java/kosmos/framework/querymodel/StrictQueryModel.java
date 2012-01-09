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
@SuppressWarnings("rawtypes")
public class StrictQueryModel extends QueryModel {

	/** the parameter */
	private OrmQueryParameter parameter = null;
	
	/**
	 * @param query
	 */
	public StrictQueryModel(LimitedOrmQuery query){
		parameter = query.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.querymodel.QueryModel#accept(kosmos.framework.querymodel.VisitableQueryProcessor)
	 */
	@Override
	public void accept(VisitableQueryProcessor updater){
		updater.getResultList(this);
	}

	/**
	 * @return the request
	 */
	@SuppressWarnings("unchecked")
	public OrmQueryParameter<Object> getRequest() {
		return parameter;
	}

}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;

/**
 * QueryModel for OrmQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeQueryModel extends QueryModel {

	/** the parameter */
	private FreeQueryParameter parameter = null;
	
	/** the baseClass */
	private Class<AbstractNativeQuery> baseClass = null;

	/** true:inherits the previous result */
	private boolean continuePreviousResult = false;
	
	
	/**
	 * @param query
	 */
	@SuppressWarnings("unchecked")
	public NativeQueryModel(AbstractNativeQuery query){
		parameter = query.getCurrentParams();
		baseClass = (Class<AbstractNativeQuery>)query.getClass();
	}
	
	/**
	 * @see kosmos.framework.querymodel.QueryModel#accept(kosmos.framework.querymodel.QueryProcessor)
	 */
	@Override
	public void accept(QueryProcessor updater){
		updater.getResultList(this);
	}
	
	/**
	 * @return the parameter
	 */
	public FreeQueryParameter getRequest() {
		return parameter;
	}
	
	/**
	 * @return the baseClass
	 */
	public Class<AbstractNativeQuery> getBaseClass() {
		return baseClass;
	}
	
	/**
	 * @return the continuePreviousResult
	 */
	public boolean isContinuePreviousResult() {
		return continuePreviousResult;
	}

	/**
	 * Sets the continuePreviousResult to true.
	 */
	public void setContinuePreviousResult() {
		this.continuePreviousResult = true;
	}

}

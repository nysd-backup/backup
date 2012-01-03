/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import kosmos.framework.sqlclient.api.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;

/**
 * QueryModel for OrmQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeUpdateModel extends QueryModel {

	/** the parameter */
	private FreeUpdateParameter parameter = null;
	
	/** the baseClass */
	private Class<AbstractNativeUpdate> baseClass = null;
	
	/** true:inherits the previous result */
	private boolean continuePreviousResult = false;
	
	/**
	 * @param query
	 */
	@SuppressWarnings("unchecked")
	public NativeUpdateModel(AbstractNativeUpdate update){
		parameter = update.getCurrentParams();
		baseClass = ( Class<AbstractNativeUpdate>)update.getClass();
	}
	
	/**
	 * @see kosmos.framework.querymodel.QueryModel#accept(kosmos.framework.querymodel.QueryProcessor)
	 */
	@Override
	public void accept(QueryProcessor updater){
		updater.update(this);
	}

	/**
	 * @return the parameter
	 */
	public FreeUpdateParameter getRequest() {
		return parameter;
	}
	
	/**
	 * @return the baseClass
	 */
	public Class<AbstractNativeUpdate> getBaseClass(){
		return this.baseClass;
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

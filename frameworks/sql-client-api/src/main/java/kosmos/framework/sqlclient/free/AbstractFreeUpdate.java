/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import kosmos.framework.sqlclient.free.strategy.InternalQuery;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractFreeUpdate {
	
	/** the query */
	private FreeUpdateParameter parameter;
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @return the parameter
	 */
	public FreeUpdateParameter getParameter(){
		return this.parameter;
	}
	
	/**
	 * @return the internalQuery
	 */
	protected InternalQuery getInternalQuery(){
		return this.internalQuery;
	}
	
	/**
	 * Constructor
	 */
	public AbstractFreeUpdate(){
		parameter = new FreeUpdateParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public AbstractFreeUpdate setParameter(String arg0, Object arg1) {
		parameter.getParam().put(arg0,arg1);
		return this;
	}
	
	/**
	 * Sets the branch parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public AbstractFreeUpdate setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return this;
	}
	
	/**
	 * Sets the query hints.
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public AbstractFreeUpdate setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return this;
	}

	/**
	 * Updates the data.
	 * @return the updated count
	 */
	public int update(){
		return internalQuery.executeUpdate(parameter);
	}
	
}


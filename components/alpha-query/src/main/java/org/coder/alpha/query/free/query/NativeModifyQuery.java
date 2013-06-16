/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.query;

import org.coder.alpha.query.free.QueryUtils;


/**
 * Concrete native modifying query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeModifyQuery extends AbstractModifyQuery{
	
	/**
	 * Set the parameters 
	 * @param arguments the arguments
	 * @return self
	 */
	public NativeModifyQuery setArguments(Object arguments){
		QueryUtils.setParameter(getParameter().getParam(), arguments);
		return this;
	}
	
	/**
	 * @param queryId the queryId
	 */
	public void setQueryId(String queryId){
		getParameter().setQueryId(queryId);
		getParameter().setSql("@"+queryId);
	}
	
	/**
	 * Set the parameter 
	 * @param name the name
	 * @param value the value
	 * @return self
	 */
	public NativeModifyQuery set(String name, Object value){
		setParameter(name, value);
		return this;
	}
}

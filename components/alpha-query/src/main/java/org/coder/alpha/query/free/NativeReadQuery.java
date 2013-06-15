/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;



/**
 * Concrete native reading query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeReadQuery extends AbstractReadQuery{

	/**
	 * Constructor.
	 */
	public NativeReadQuery(){
		setResultType(Record.class);
	}
	
	/**
	 * Set the parameters 
	 * @param arguments the arguments
	 * @return self
	 */
	public NativeReadQuery setArguments(Object arguments){
		QueryUtils.setParameter(getParameter().getParam(), arguments);
		return this;
	}
	
	/**
	 * Sets the result type.
	 * @param resultType the result type
	 * @return self
	 */
	public NativeReadQuery setResultType(Class<?> resultType){
		getParameter().setResultType(resultType);
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
	public NativeReadQuery set(String name, Object value){
		setParameter(name, value);
		return this;
	}
	
}

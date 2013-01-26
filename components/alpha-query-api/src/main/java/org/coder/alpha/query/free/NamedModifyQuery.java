/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;


/**
 * Concrete native modifying query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NamedModifyQuery extends AbstractNamedModifyQuery{
	
	/**
	 * Set the parameters 
	 * @param arguments the arguments
	 * @return self
	 */
	public NamedModifyQuery setArguments(Object arguments){
		QueryUtils.setParameter(getParameter().getParam(), arguments);
		return this;
	}
	
	/**
	 * @param queryId the queryId
	 */
	public void setQueryId(String queryId){
		getParameter().setQueryId(queryId);
	}
	
	/**
	 * @param sql the sql
	 */
	public void setJpql(String jpql){
		getParameter().setSql(jpql);
	}
}

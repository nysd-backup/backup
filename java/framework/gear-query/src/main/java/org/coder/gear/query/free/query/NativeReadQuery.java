/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.query;

import org.coder.gear.query.free.QueryUtils;
import org.coder.gear.query.free.result.Record;
import org.coder.gear.query.free.result.TotalList;



/**
 * Concrete native reading query.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class NativeReadQuery extends AbstractReadQuery{
	
	private boolean autoSearchAgain;
	
	/**
	 * @param autoSearchAgain to set
	 * @return selfs
	 */
	public NativeReadQuery setAutoSearchAgain(boolean autoSearchAgain){
		this.autoSearchAgain = autoSearchAgain;
		return this;
	}
	

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
	
	/**
	 * @see org.coder.alpha.query.free.AbstractNativeReadQuery#getTotalResult()
	 */
	@Override
	public <T> TotalList<T> getTotalResult() {
		TotalList<T> result = super.getTotalResult();
		if(autoSearchAgain){
			PagingHandler handler = new PagingHandler();
			while( handler.shouldRetry(getParameter(), result)) {
				setFirstResult(handler.getNewStart());
				result = super.getTotalResult();
			}
		}
		return result;
	}
	
}

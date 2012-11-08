/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.free;


/**
 * Concrete native modifying query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeModifyQuery extends AbstractNativeModifyQuery{
	
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

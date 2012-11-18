/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.free;


/**
 * Concrete native reading query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeReadQuery extends AbstractNativeReadQuery{

	public NativeReadQuery(){
		setResultType(Record.class);
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

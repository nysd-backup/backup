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
public class NamedReadQuery extends AbstractNamedReadQuery{

	public NamedReadQuery(){
		setResultType(Record.class);
	}
	
	/**
	 * Sets the result type.
	 * @param resultType the result type
	 * @return self
	 */
	public NamedReadQuery setResultType(Class<?> resultType){
		getParameter().setResultType(resultType);
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

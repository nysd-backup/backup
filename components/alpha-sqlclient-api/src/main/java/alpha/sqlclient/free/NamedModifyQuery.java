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
public class NamedModifyQuery extends AbstractNativeModifyQuery{
	
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

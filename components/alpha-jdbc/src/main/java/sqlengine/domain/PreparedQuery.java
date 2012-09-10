/**
 * Copyright 2011 the original author
 */
package sqlengine.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sqlengine.strategy.StatementProvider;

/**
 * A prepared query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PreparedQuery {

	/** the query statement */
	private String statement;
	
	/** the binding target */
	private final List<List<Object>> bindList;
	
	/** the id */
	private final String queryId;
	
	/**
	 * @param statement the statement to set
	 * @param bindList the bindlist to set
	 * @param queryId the id to set
	 */
	public PreparedQuery(String statement, List<List<Object>> bindList, String queryId){
		this.statement = statement;
		this.bindList = bindList;
		this.queryId = queryId;
	}
	
	/**
	 * @return the queryId
	 */
	public String getQueryId(){
		return this.queryId;
	}

	/**
	 * @return the bindList
	 */
	public List<List<Object>> getBindList() {
		return bindList;
	}

	/**
	 * @return the first of the bind target
	 */
	public List<Object> getFirstList(){
		return getBindList().get(0);
	}

	/**
	 * @return the statement
	 */
	public String getQueryStatement() {
		return statement;
	}
	
	/**
	 * Gets the StatementWrapper.
	 * @param con the connection
	 * @param provider the provider
	 * @return the StatementWrapper
	 * @throws SQLException
	 */
	public StatementWrapper getStatement(Connection con,StatementProvider provider)
	throws SQLException {
		PreparedStatement stmt = provider.createStatement(queryId, con, statement);	
		return new StatementWrapper(stmt,bindList,provider);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(List<Object> e : bindList){					
			builder.append("[");
			boolean first = true;
			for(Object o : e){
				if(first){
					first = false;
				}else{
					builder.append(",");
				}
				builder.append((o instanceof String) ? "\'" + o  + "\'" : o);
			}
			builder.append("]\n");			
		}
		String query = String.format("executing sql = \n%s\n%s",statement,builder.toString());		
		
		StringBuilder convertedQuery = new StringBuilder();
		for(List<Object> param : bindList){
			Iterator<Object> ite = param.iterator();
			String converted = statement;
			
			//?にパラメータを埋め込む
			while(converted.contains("?")){
				if( !ite.hasNext() ){
					throw new IllegalStateException("count of ? is different from parameter count");
				}
				Object v = ite.next();
				converted = StringUtils.replaceOnce(converted, "?", (v instanceof String) ? "\'" + v  + "\'" : String.valueOf(v));		
			}	
			convertedQuery.append(String.format("complete sql = \n%s",converted));
		}
		
		return query + ";" + convertedQuery.toString();
	}


}

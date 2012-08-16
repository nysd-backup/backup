/**
 * Copyright 2011 the original author
 */
package sqlengine.builder;

import java.util.List;

import core.logics.utility.QueryUtils;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PreparedQuery {

	private final String statement;
	
	private final List<List<Object>> bindList;
	
	public PreparedQuery(String statement, List<List<Object>> bindList){
		this.statement = statement;
		this.bindList = bindList;
	}

	/**
	 * @return the bindList
	 */
	public List<List<Object>> getBindList() {
		return bindList;
	}

	/**
	 * 
	 */
	public List<Object> getFirstList(){
		return getBindList().get(0);
	}

	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		//TODO
		return null;
	}


}

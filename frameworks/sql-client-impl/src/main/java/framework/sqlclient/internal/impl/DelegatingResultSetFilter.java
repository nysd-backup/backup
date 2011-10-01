/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlengine.executer.RecordFilter;

/**
 * The filter to delegate for <code>ResultSet</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingResultSetFilter<T> implements RecordFilter<T>{

	/** the filter */
	private ResultSetFilter<T> filter = null;
	
	/**
	 * @param delegate　the delegate to set
	 */
	public DelegatingResultSetFilter(ResultSetFilter<T> delegate){
		this.filter = delegate;
	}
	
	/**
	 * @see framework.sqlengine.executer.RecordFilter#edit(java.lang.Object)
	 */
	@Override
	public T edit(T data) {
		return filter.edit(data);
	}

}

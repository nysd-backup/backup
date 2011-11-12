/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.impl;

import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlengine.executer.RecordFilter;

/**
 * The filter to delegate for <code>ResultSet</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingResultSetFilter<T> implements RecordFilter<T>{

	/** the filter */
	private final ResultSetFilter<T> filter;
	
	/**
	 * @param delegate　the delegate to set
	 */
	public DelegatingResultSetFilter(ResultSetFilter<T> delegate){
		this.filter = delegate;
	}
	
	/**
	 * @see kosmos.framework.sqlengine.executer.RecordFilter#edit(java.lang.Object)
	 */
	@Override
	public T edit(T data) {
		if(filter != null){
			return filter.edit(data);
		}else{
			return data;
		}
	}

}

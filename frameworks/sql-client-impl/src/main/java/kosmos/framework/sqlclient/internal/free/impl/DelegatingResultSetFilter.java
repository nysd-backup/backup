/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlengine.executer.RecordFilter;

/**
 * The filter to delegate for <code>ResultSet</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingResultSetFilter implements RecordFilter{

	/** the filter */
	private final ResultSetFilter filter;
	
	/**
	 * @param delegate　the delegate to set
	 */
	public DelegatingResultSetFilter(ResultSetFilter delegate){
		this.filter = delegate;
	}
	
	/**
	 * @see kosmos.framework.sqlengine.executer.RecordFilter#edit(java.lang.Object)
	 */
	@Override
	public <T> T edit(T data) {
		if(filter != null){
			return filter.edit(data);
		}else{
			return data;
		}
	}

}

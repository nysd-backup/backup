/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlengine.executer.RecordFilter;

/**
 * リザルトセットフィルター.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DelegatingResultSetFilter<T> implements RecordFilter<T>{

	/** フィルター */
	private ResultSetFilter<T> filter = null;
	
	/**
	 * @param delegate　delegate
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

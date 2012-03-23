/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.Iterator;
import java.util.List;

import kosmos.framework.sqlclient.api.Query;


/**
 *　The query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class NativeQueryImpl extends AbstractQuery implements NativeQuery{
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> list = internalQuery.getResultList(condition);		
		return list;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.AbstractQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public NativeQuery setBranchParameter(String arg0, Object arg1) {
		condition.getBranchParam().put(arg0, arg1);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@Override
	public NativeQuery setFilter(ResultSetFilter filter) {
		condition.setFilter(filter);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public NativeResult getTotalResult() {
		return (NativeResult)internalQuery.getTotalResult(condition);		
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public long count() {
		return internalQuery.count(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Query> T setHint(String key, Object value) {
		condition.getHints().put(key, value);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult(kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public long getFetchResult(QueryCallback<?> callback) {
		List<Object> lazyList = getFetchResult();
		Iterator<Object> iterator = lazyList.iterator();
		long count = 0;
		try{
			while(iterator.hasNext()){	
				((QueryCallback<Object>)callback).handleRow(iterator.next(), count);
				count++;
			}
		}finally{
			lazyList.clear();
		}
		return count;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		List<Object> lazyList = internalQuery.getFetchResult(condition);
		return (List<T>)lazyList;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setSql(java.lang.String)
	 */
	@Override
	public NativeQuery setSql(String sql) {
		getCurrentParams().setSql(sql);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setSqlId(java.lang.String)
	 */
	@Override
	public NativeQuery setSqlId(String sqlId) {
		getCurrentParams().setQueryId(sqlId);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setResultType(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public NativeQuery setResultType(Class resultType) {
		getCurrentParams().setResultType(resultType);
		return this;
	}

}

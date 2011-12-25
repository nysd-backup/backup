package kosmos.framework.sqlclient.internal.free;

import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;

public interface InternalQuery {

	/**
	 * @param filter the filter to set
	 * @return self
	 */ 
	public abstract void setFilter(ResultSetFilter filter);

	/**
	 * @return the result
	 */
	public abstract NativeResult getTotalResult();

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	public abstract <T> List<T> getFetchResult();

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#count()
	 */
	public abstract int count();

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#getResultList()
	 */
	public abstract <T> List<T> getResultList();

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	public abstract <T> T getSingleResult();

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#executeUpdate()
	 */
	public abstract int executeUpdate();

	/**
	 * @param arg0 the key 
	 * @param arg1 the value
	 */
	public void setBranchParameter(String arg0 , Object arg1);

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() ;

	/**
	 * @return the maxResults
	 */
	public int getMaxResults(); 

	/**
	 * @param arg0 the start position
	 * @return self
	 */
	public void setFirstResult(int arg0) ;

	/**
	 * @param arg0 the max results
	 * @return self
	 */
	public void setMaxResults(int arg0);

	/**
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public void setParameter(String arg0, Object arg1) ;
	
	/**
	 * @return the hints
	 */
	public Map<String,Object> getHints();

	/**
	 * @param hints the hints to set
	 */
	public void setHint(String key , Object value) ;


}
/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kosmos.framework.sqlclient.api.Query;


/**
 * The free writable query.
 * The base of all the query.
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeQuery<D extends FreeQuery> implements FreeQuery{
	
	/** the delegate */
	protected D delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	protected <T extends FreeQuery> T setDelegate(D delegate){
		this.delegate = delegate;
		return (T)this;
	}
	
	/**
	 * @return the delegate
	 */
	public D getDelegate(){
		return delegate;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#getCurrentParams()
	 */
	@Override
	public FreeQueryParameter getCurrentParams() {
		return delegate.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setCondition(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public void setCondition(FreeQueryParameter parameter) {
		delegate.setCondition(parameter);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */

	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		return (List<T>)delegate.getResultList();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public long count() {
		return delegate.count();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Query> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}


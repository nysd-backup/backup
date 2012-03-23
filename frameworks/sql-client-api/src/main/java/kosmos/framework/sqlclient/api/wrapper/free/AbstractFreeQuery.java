/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.free;

import java.util.List;

import kosmos.framework.sqlclient.api.free.FreeQuery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * The free writable query.
 * The base of all the query.
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeQuery<D extends FreeQuery> {
	
	/** the delegate */
	protected D delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public <T extends AbstractFreeQuery<D>> T setDelegate(D delegate){
		this.delegate = delegate;
		return (T)this;
	}
	
	/**
	 * Sets the branch parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeQuery<D>> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * Gets the query result.
	 * @return the result
	 */
	public <T> List<T> getResultList() {
		return (List<T>)delegate.getResultList();
	}
	
	/**
	 * Gets the query result.
	 * @return the first result
	 */
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
	}
	
	/**
	 * Gets the hit count
	 * @return the hit count
	 */
	public long count() {
		return delegate.count();
	}

	/**
	 * Sets the first result.
	 * @param arg0 the start position that starts with 1
	 * @return self
	 */
	public <T extends AbstractFreeQuery<D>> T setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * Sets the max results.
	 * @param arg0 the size to get
	 * @return self
	 */
	public <T extends AbstractFreeQuery<D>> T setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (T)this;
	}

	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeQuery<D>> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * Sets the query hints
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractFreeQuery<D>> T setHint(String arg0, Object arg1) {
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


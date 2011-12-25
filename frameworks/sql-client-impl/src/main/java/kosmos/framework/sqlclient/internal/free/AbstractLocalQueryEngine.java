/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.FreeQuery;

/**
 * The local query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalQueryEngine<D extends InternalQuery> implements FreeQuery{

	/** the delegate */
	protected final D delegate;

	/**
	 * @param delegate the query
	 */
	public AbstractLocalQueryEngine(D delegate){
		this.delegate = delegate;		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
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
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1){
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

}

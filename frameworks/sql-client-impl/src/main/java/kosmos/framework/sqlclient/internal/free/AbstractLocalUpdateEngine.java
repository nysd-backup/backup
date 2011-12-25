/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free;

import kosmos.framework.sqlclient.api.free.FreeUpdate;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalUpdateEngine<D extends InternalQuery> implements FreeUpdate{

	/** the query */
	protected final D delegate;
	
	/**
	 * @param delegate the query to delegate
	 */
	public AbstractLocalUpdateEngine(D delegate){
		this.delegate = delegate;		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setParameter(String arg0 , Object arg1){
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}

}

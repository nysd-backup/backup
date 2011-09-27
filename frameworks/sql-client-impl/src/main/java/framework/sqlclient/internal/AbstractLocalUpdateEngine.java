/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal;

import framework.sqlclient.api.free.FreeUpdate;

/**
 * Update実行エンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalUpdateEngine<D extends AbstractInternalQuery> implements FreeUpdate{

	/** クエリ */
	protected final D delegate;
	
	/**
	 * @param delegate クエリ
	 */
	public AbstractLocalUpdateEngine(D delegate){
		this.delegate = delegate;		
	}
	
	/**
	 * @see framework.sqlclient.api.free.FreeUpdate#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setParameter(String arg0 , Object arg1){
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}

}

/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractUpdate<D extends FreeUpdate> implements FreeUpdate{
	
	/** the delegate */
	protected D delegate;
	
	/**
	 * @param delegate delegate
	 */
	<T extends FreeUpdate> T setDelegate(D delegate){
		this.delegate = delegate;
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.free.FreeUpdate#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update(){
		return delegate.update();
	}
}


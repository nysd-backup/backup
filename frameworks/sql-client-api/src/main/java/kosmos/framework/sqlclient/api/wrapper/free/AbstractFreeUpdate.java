/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.free;

import kosmos.framework.sqlclient.api.free.FreeUpdate;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeUpdate<D extends FreeUpdate>{
	
	/** the delegate */
	protected D delegate;
	
	/**
	 * @param delegate delegate
	 */
	public <T extends AbstractFreeUpdate<D>> T setDelegate(D delegate){
		this.delegate = delegate;
		return (T)this;
	}
	
	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeUpdate<D>> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * Sets the branch parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeUpdate<D>> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * Sets the query hints.
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractFreeUpdate<D>> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}

	/**
	 * Updates the data.
	 * @return the updated count
	 */
	public int update(){
		return delegate.update();
	}
	
}


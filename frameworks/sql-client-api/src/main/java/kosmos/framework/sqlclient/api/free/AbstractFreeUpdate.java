/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.Update;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeUpdate<D extends FreeUpdate> implements FreeUpdate{
	
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
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#getCurrentParams()
	 */
	@Override
	public FreeUpdateParameter getCurrentParams(){
		return delegate.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setCondition(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public void setCondition(FreeUpdateParameter parameter) {
		delegate.setCondition(parameter);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Update> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update(){
		return delegate.update();
	}
	

	/**
	 * @see kosmos.framework.sqlclient.api.Update#addBatch()
	 */
	@Override
	public <T extends Update> T addBatch() {
		delegate.addBatch();
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#batchUpdate()
	 */
	@Override
	public int[] batchUpdate() {
		return delegate.batchUpdate();
	}

	
}


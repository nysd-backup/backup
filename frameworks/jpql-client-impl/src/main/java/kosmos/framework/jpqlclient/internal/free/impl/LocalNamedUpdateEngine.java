/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import kosmos.framework.jpqlclient.api.free.NamedUpdate;
import kosmos.framework.sqlclient.api.Update;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.internal.free.AbstractLocalUpdateEngine;
import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The named update engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalNamedUpdateEngine extends AbstractLocalUpdateEngine implements NamedUpdate{

	/**
	 * @param delegate the delegate to set.
	 */
	public LocalNamedUpdateEngine(InternalQuery internalQuery, FreeUpdateParameter param) {
		super(internalQuery,param);		
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Update> T setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return (T)this;
	}

	/**
	 * @return 
	 * @see kosmos.framework.sqlclient.api.Update#addBatch()
	 */
	@Override
	public <T extends Update> T addBatch() {
		parameter.addBatch();
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#batchUpdate()
	 */
	@Override
	public int[] batchUpdate() {
		return internalQuery.batchUpdate(parameter);
	}


}

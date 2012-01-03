/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import kosmos.framework.sqlclient.api.Update;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.free.NativeUpdate;
import kosmos.framework.sqlclient.internal.free.AbstractLocalUpdateEngine;
import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalUpdateEngine extends AbstractLocalUpdateEngine implements NativeUpdate{

	/**
	 * @param param the internalQuery to set
	 * @param parameter the parameter to set
	 */
	public LocalUpdateEngine(InternalQuery internalQuery, FreeUpdateParameter parameter) {
		super(internalQuery,parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Update> T setHint(String key, Object value) {
		parameter.getHints().put(key, value);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#addBatch()
	 */
	@SuppressWarnings("unchecked")
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

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.config;

/**
 * Connection Property.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConnectionProperty {
	
	/** keep connection between request or thread */
	private KeepAliveScope keepAliveScope = KeepAliveScope.REQUEST;
	
//	/** pool connection *
	private boolean poolable = false;

	/**
	 * @return the keepAliveScope
	 */
	public KeepAliveScope getKeepAliveScope() {
		return keepAliveScope;
	}

	/**
	 * @param keepAliveScope the keepAliveScope to set
	 */
	public void setKeepAliveScope(KeepAliveScope keepAliveScope) {
		this.keepAliveScope = keepAliveScope;
	}

	/**
	 * @return the poolable
	 */
	public boolean isPoolable() {
		return poolable;
	}

	/**
	 * @param poolable the poolable to set
	 */
	public void setPoolable(boolean poolable) {
		this.poolable = poolable;
	}

}

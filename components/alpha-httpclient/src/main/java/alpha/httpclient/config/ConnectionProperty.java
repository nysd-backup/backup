/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConnectionProperty {
	
	private int socketTimeout = -1;
	
	private int connectionTimeout = -1;
	
	private boolean poolable = true;

	/**
	 * @return the socketTimeout
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * @param socketTimeout the socketTimeout to set
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * @return the connectionTimeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * @param connectionTimeout the connectionTimeout to set
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
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

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs.balancer;

import java.net.SocketException;

/**
 * Requester.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface Requester<T> {

	/**
	 * @return response
	 */
	T request(String target) throws SocketException;
}

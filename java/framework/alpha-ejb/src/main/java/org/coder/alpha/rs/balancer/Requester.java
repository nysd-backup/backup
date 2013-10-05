/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.rs.balancer;

import java.net.SocketException;

/**
 * Requester.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface Requester<T> {

	/**
	 * @return response
	 */
	T request(String target) throws SocketException;
}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.core;

import org.coder.alpha.httpclient.config.RequestProperty;



/**
 * HttpClientFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface HttpClientFactory {

	/**
	 * Creates the service.
	 * @param serviceType the service type
	 * @param property
	 * @return
	 */
	public <T> T createService(Class<T> serviceType,RequestProperty property); 
}

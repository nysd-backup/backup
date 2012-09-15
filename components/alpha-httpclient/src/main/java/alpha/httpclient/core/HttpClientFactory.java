/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.core;

import alpha.httpclient.config.RequestProperty;



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

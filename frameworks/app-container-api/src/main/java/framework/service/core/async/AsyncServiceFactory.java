/**
 * Copyright 2011 the original author
 */
package framework.service.core.async;

/**
 * The factory to create asynchronous services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AsyncServiceFactory {

	/**
	 * @param <T> the type
	 * @param serviceType the serviceType to set
	 * @return the service
	 */
	public <T> T create(Class<T> serviceType);
}

/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

/**
 * Provides the factory of messaging client.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageClientFactoryProvider {

	/**
	 * @return MessageClientFactory
	 */
	MessageClientFactory getMessageClientFactory();
}

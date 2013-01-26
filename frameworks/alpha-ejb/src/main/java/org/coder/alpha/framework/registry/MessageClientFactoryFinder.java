/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import org.coder.alpha.framework.messaging.client.MessageClientFactory;

/**
 * Provides the factory of messaging client.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageClientFactoryFinder {

	/**
	 * @return MessageClientFactory
	 */
	MessageClientFactory getMessageClientFactory();
}

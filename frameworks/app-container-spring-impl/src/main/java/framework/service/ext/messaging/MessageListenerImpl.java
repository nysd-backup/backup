/**
 * Copyright 2011 the original author
 */
package framework.service.ext.messaging;

import framework.api.service.ServiceActivator;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.messaging.AbstractMessageListener;

/**
 * A listener for MDB and MDP.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageListenerImpl extends AbstractMessageListener{

	/**
	 * @see framework.service.core.messaging.AbstractMessageListener#createListener()
	 */
	@Override
	protected ServiceActivator createListener() {
		return ServiceLocator.lookupByInterface(ServiceActivator.class);
	}

}

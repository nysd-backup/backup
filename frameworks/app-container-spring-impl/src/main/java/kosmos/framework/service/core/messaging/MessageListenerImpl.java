/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.messaging.AbstractMessageListener;

/**
 * A listener for MDB and MDP.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageListenerImpl extends AbstractMessageListener{

	/**
	 * @see kosmos.framework.service.core.messaging.AbstractMessageListener#createListener()
	 */
	@Override
	protected ServiceActivator createListener() {
		return ServiceLocator.lookupByInterface(ServiceActivator.class);
	}

}

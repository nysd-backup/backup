/**
 * Copyright 2011 the original author
 */
package framework.service.ext.messaging;

import framework.api.service.DelegatingServiceInvoker;
import framework.service.core.messaging.AbstractMessageListener;
import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * A listener for MDB.
 * 
 * <pre>
 * the properties of <code>@ActivationConfigProperty</code> is under.
 *
 * destinationType 
 *   - this is either Topic or Queue
 * connectionFactoryJndiName 
 *   - specifies the JNDI name of the connection factory that should create the JMS connection
 * destinationName 
 *   - specifies that we are listening for messages arriving at a destination with the JNDI name
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageListenerImpl extends AbstractMessageListener{

	/**
	 * @see framework.service.core.messaging.AbstractMessageListener#createListener()
	 */
	@Override
	protected DelegatingServiceInvoker createListener() {
		return ServiceLocatorImpl.getComponentBuilder().createDelegatingServiceInvoker();
	}

}

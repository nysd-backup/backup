/**
 * Copyright 2011 the original author
 */
package framework.service.ext.messaging;

import framework.api.service.RequestListener;
import framework.service.core.messaging.AbstractMessageListener;
import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * メッセージリスナー.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageListenerImpl extends AbstractMessageListener{

	/**
	 * @see framework.service.core.messaging.AbstractMessageListener#createListener()
	 */
	@Override
	protected RequestListener createListener() {
		return ServiceLocatorImpl.getComponentBuilder().createRequestListener();
	}

}

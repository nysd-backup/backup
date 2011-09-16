/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import framework.api.service.RequestListener;
import framework.service.core.messaging.AbstractMessageListener;
import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * メッセージリスナー.
 *
 * @author yoshida-n
 * @version	created.
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

/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import framework.api.service.RequestListener;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.messaging.AbstractMessageListener;

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
		return ServiceLocator.lookupDefault(RequestListener.class);
	}

}

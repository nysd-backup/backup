/**
 * Use is subject to license terms.
 */
package framework.service.ext.listener;

import framework.api.service.RequestListener;
import framework.service.core.listener.AbstractRequestListener;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.ServiceContextImpl;

/**
 * リクエストリスナー.
 *
 * @author yoshida-n
 * @version	2011/05/12 created.
 */
public class RequestListenerImpl extends AbstractRequestListener implements RequestListener{

	/**
	 * @see framework.service.core.listener.AbstractRequestListener#createContext()
	 */
	@Override
	protected ServiceContext createContext() {
		return new ServiceContextImpl();
	}

}
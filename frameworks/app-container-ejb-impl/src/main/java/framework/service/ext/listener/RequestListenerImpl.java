/**
 * Copyright 2011 the original author
 */
package framework.service.ext.listener;

import framework.api.service.RequestListener;
import framework.service.core.listener.AbstractRequestListener;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.ServiceContextImpl;

/**
 * A listener for calling request.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.PersistenceContext;
import kosmos.framework.sqlclient.api.PersistenceContextProvider;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceContextProviderImpl implements PersistenceContextProvider {

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceContextProvider#getContext()
	 */
	@Override
	public PersistenceContext getContext() {
		ServiceTestContextImpl t = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		return t.getContext();
	}

}

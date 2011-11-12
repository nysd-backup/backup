/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.ejb.Stateless;

import kosmos.framework.api.query.services.NativeQueryService;
import kosmos.framework.service.core.locator.ServiceLocatorImpl;
import kosmos.framework.service.core.services.AbstractNativeQueryService;
import kosmos.framework.sqlclient.api.free.QueryFactory;


/**
 * A native query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class NativeQueryServiceImpl extends AbstractNativeQueryService implements NativeQueryService{

	/**
	 * @see kosmos.framework.service.core.services.AbstractNativeQueryService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createNativeQueryFactory();
	}
	

}

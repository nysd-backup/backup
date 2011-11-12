/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.ejb.Stateless;

import kosmos.framework.api.query.services.PagingService;
import kosmos.framework.service.core.locator.ServiceLocatorImpl;
import kosmos.framework.service.core.services.AbstractLatestPagingService;
import kosmos.framework.sqlclient.api.free.QueryFactory;


/**
 * A paging service.
 * 
 * <pre>
 * Always execute SQL to get latest data.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class LatestPagingServiceImpl extends AbstractLatestPagingService implements PagingService{
	
	/**
	 * @see kosmos.framework.service.core.services.AbstractLatestPagingService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createNativeQueryFactory();
	}
}

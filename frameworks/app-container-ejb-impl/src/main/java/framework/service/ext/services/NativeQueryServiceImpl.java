/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.Stateless;

import framework.api.query.services.NativeQueryService;
import framework.service.core.services.AbstractNativeQueryService;
import framework.service.ext.locator.ServiceLocatorImpl;
import framework.sqlclient.api.free.QueryFactory;

/**
 * A native query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class NativeQueryServiceImpl extends AbstractNativeQueryService implements NativeQueryService{

	/**
	 * @see framework.service.core.services.AbstractNativeQueryService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createNativeQueryFactory();
	}
	

}

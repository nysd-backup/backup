/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.query.paging.impl;

import kosmos.framework.api.query.services.PagingService;
import kosmos.framework.web.core.api.query.paging.PagingContexFactory;
import kosmos.framework.web.core.api.query.paging.PagingContext;
import kosmos.framework.web.core.api.service.ServiceCallable;
import kosmos.framework.web.core.api.service.ServiceFacade;

/**
 *　The factory to create the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ServiceCallable
public class PagingContextFactoryImpl implements PagingContexFactory{

	@ServiceFacade(alias="LatestPagingServiceImpl")
	private PagingService pagingService;
	
	/**
	 * @see kosmos.framework.web.core.api.query.paging.PagingContexFactory#create()
	 */
	@Override
	public PagingContext create() {
		return new PagingContextImpl(pagingService);
	}

}

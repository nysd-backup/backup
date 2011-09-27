/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query.paging.impl;

import framework.api.query.services.PagingService;
import framework.web.core.api.query.paging.PagingContexFactory;
import framework.web.core.api.query.paging.PagingContext;
import framework.web.core.api.service.ServiceCallable;
import framework.web.core.api.service.ServiceFacade;

/**
 * ページングコンテキストファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ServiceCallable
public class PagingContextFactoryImpl implements PagingContexFactory{

	@ServiceFacade(alias="LatestPagingServiceImpl")
	private PagingService pagingService;
	
	/**
	 * @see framework.web.core.api.query.paging.PagingContexFactory#create()
	 */
	@Override
	public PagingContext create() {
		return new PagingContextImpl(pagingService);
	}

}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.query.paging.impl;

import kosmos.framework.core.services.PagingService;
import kosmos.framework.web.core.api.query.paging.PagingContexFactory;
import kosmos.framework.web.core.api.query.paging.PagingContext;

/**
 *　The factory to create the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PagingContextFactoryImpl implements PagingContexFactory{

	private PagingService pagingService;

	/**
	 * @param pagingService the pagingService to set
	 */
	public void setPagingService(PagingService pagingService){
		this.pagingService = pagingService;
	}
	
	/**
	 * @see kosmos.framework.web.core.api.query.paging.PagingContexFactory#create()
	 */
	@Override
	public PagingContext create() {
		return new PagingContextImpl(pagingService);
	}

}

/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query.paging.impl;

import java.util.List;
import framework.api.query.services.PagingRequest;
import framework.api.query.services.PagingResult;
import framework.api.query.services.PagingService;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.QueryAccessor;
import framework.web.core.api.query.WebNativeQueryEngine;
import framework.web.core.api.query.paging.PagingContext;

/**
 * The paging context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class PagingContextImpl implements PagingContext {
	
	/** the service */
	private final PagingService pagingService;

	/** the one page size  */
	private int pageSize;
	
	/** the request */
	private PagingRequest request;
	
	/** the result */
	private PagingResult result;
	
	/**
	 * @param pagingService the service
	 */
	PagingContextImpl(PagingService pagingService){
		this.pagingService = pagingService;
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getCurrentPageData()
	 */
	@Override
	public List getCurrentPageData(){
		return result.getCurrentPageData();
	}
	
	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getTotalCount()
	 */
	@Override
	public int getTotalCount() {
		return result.getTotalCount();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getTotalPage()
	 */
	@Override
	public int getTotalPage() {
		int totalCount = getTotalCount();
		int mod = totalCount % pageSize; 
		return mod == 0 ? totalCount/pageSize : totalCount /pageSize + 1;  
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getCurrentPageNo()
	 */
	@Override
	public int getCurrentPageNo() {
		return result.getCurrentPageNo();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#prepare(framework.sqlclient.api.free.AbstractNativeQuery, int)
	 */
	@Override
	public List prepare(AbstractNativeQuery query,int pageSize){

		WebNativeQueryEngine engine = (WebNativeQueryEngine)QueryAccessor.getDelegate(query);
		PagingRequest request = new PagingRequest();
		request.setInternal(engine.getRequest());
		request.setPageSize(pageSize);
		
		result = pagingService.prepare(request);	
		this.request = request;
		this.pageSize = pageSize;
		
		return getCurrentPageData();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getPageData(int)
	 */
	@Override
	public List getPageData(int pageNo) {

		//例えばページサイズ50件で2ページ目が指定されたた場合51からにする
		if( pageNo > getTotalPage() ){
			throw new IllegalArgumentException("'pageNo' must be 'totalPageCount' and less ");
		}	
		request.setStartPosition( (pageNo - 1)* pageSize ) ;
		request.setTotalCount(getTotalCount());
		
		result = pagingService.getPageData(request);
		
		return getCurrentPageData();
	}
	
}

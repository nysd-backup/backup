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
 * 繝壹・繧ｸ繝ｳ繧ｰ逕ｨ繧ｳ繝ｳ繝・く繧ｹ繝・
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class PagingContextImpl implements PagingContext {
	
	/** 繝壹・繧ｸ繝ｳ繧ｰ繧ｵ繝ｼ繝薙せ */
	private final PagingService pagingService;

	/** 1繝壹・繧ｸ縺ゅ◆繧翫・莉ｶ謨ｰ  */
	private int pageSize;
	
	/** 繝ｪ繧ｯ繧ｨ繧ｹ繝医ヱ繝ｩ繝｡繝ｼ繧ｿ */
	private PagingRequest request;
	
	/** 繝壹・繧ｸ繝ｳ繧ｰ諠・ｱ */
	private PagingResult result;
	
	/**
	 * @param pagingService 繧ｵ繝ｼ繝薙せ
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

		//萓九∴縺ｰ繝壹・繧ｸ繧ｵ繧､繧ｺ50莉ｶ縺ｧ2繝壹・繧ｸ逶ｮ縺梧欠螳壹＆繧後◆繧・1縺九ｉ縺ｫ縺吶ｋ
		if( pageNo > getTotalPage() ){
			throw new IllegalArgumentException("'pageNo' must be 'totalPageCount' and less ");
		}	
		request.setStartPosition( (pageNo - 1)* pageSize ) ;
		request.setTotalCount(getTotalCount());
		
		result = pagingService.getPageData(request);
		
		return getCurrentPageData();
	}
	
}

/**
 * Copyright 2011 the original author
 */
package framework.service.core.services;

import java.util.ArrayList;
import java.util.List;
import framework.api.query.services.PagingRequest;
import framework.api.query.services.PagingResult;
import framework.api.query.services.PagingService;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.QueryFactory;

/**
 * a paging service.
 * 
 * <pre>
 * Always execute SQL to get latest data.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractLatestPagingService implements PagingService{

	/**
	 * @return the factory to create the query
	 */
	protected abstract QueryFactory getQueryFactory();

	/**
	 * @see framework.api.query.services.PagingService#prepare(framework.api.query.services.PagingRequest)
	 */
	@Override
	public PagingResult prepare(PagingRequest request) {

		if( request.getStartPosition() != 0){
			throw new IllegalArgumentException("'startPosition' must be 0 for first access");
		}
		if( request.getTotalCount() != 0){
			throw new IllegalArgumentException("'totalCount' must be 0 for first access");
		}
		
		//件数取得。件数制限なしを想定しているため大量件数の場合ResultSet#next()でインクリメントするとパフォーマンスが非常に悪い。そのためcountで件数のみ取得する
		AbstractNativeQuery query = createQuery(request);	
		int totalCount = query.count();		
	
		//ページング開始
		return search(query,request.getStartPosition(),request.getPageSize(),totalCount);
	}
	
	
	/**
	 * @see framework.api.query.services.PagingService#getPageData(framework.api.query.services.PagingRequest)
	 */
	@Override
	public PagingResult getPageData(PagingRequest request) {
		
		if( request.getStartPosition() == 0){
			throw new IllegalArgumentException("'startPosition' must not be 0 ");
		}
		if( request.getTotalCount() == 0){
			throw new IllegalArgumentException("'totalCount' must not be 0 ");
		}
		
		AbstractNativeQuery query = createQuery(request);

		//ページング開始
		return search(query,request.getStartPosition(),request.getPageSize(),request.getTotalCount());
	
	}
	
	/**
	 * @param request　the request
	 * @return the query
	 */
	private AbstractNativeQuery createQuery(PagingRequest request){
		
		if( request.getInternal().getFirstResult() != 0){
			throw new IllegalArgumentException("'firstResult' must be 0 for paging");
		}
		if( request.getInternal().getMaxSize() != 0 ){
			throw new IllegalArgumentException("'maxSize' must be 0");
		}	
		if( request.getPageSize() <= 0 ){
			throw new IllegalArgumentException("'pageSize' must be greater than 0");
		}	
		Class<AbstractNativeQuery> queryClass = request.getInternal().getQueryClass(); 
		AbstractNativeQuery query = getQueryFactory().createQuery(queryClass);
		
		return ParameterConverter.setParameters(request.getInternal(), query);
	}

	/**
	 * @param query the query
	 * @param startPosition the start pointer
	 * @param pageSize the size of one page
	 * @param totalCount the total count
	 * @return the result
	 */
	private PagingResult search(AbstractNativeQuery query, int startPosition, int pageSize,int totalCount){

		//ページサイズ文のデータを取得する。
		query.setMaxResults(pageSize);
		
		List<?> currentPageData = new ArrayList<Object>();		
		int currentPageNo = 0;
		
		if(totalCount> 0){
			query.setFirstResult(startPosition);
			currentPageData = query.getResultList();						
			if(currentPageData.size() > 0){
				int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
				for(currentPageNo = 1 ; currentPageNo <= pageCount; currentPageNo++){			
					// 現在ページの先頭  < 開始位置 <　現在ページのラスト
					if( (currentPageNo -1) * pageSize -1 < startPosition 
							&& (currentPageNo * pageSize) > startPosition ){
							break;
					}
				}
			}
		}
		return new PagingResult(currentPageData, currentPageNo, totalCount);
	}
	
	
}

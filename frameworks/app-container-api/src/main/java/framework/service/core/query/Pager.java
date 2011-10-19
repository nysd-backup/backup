/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.util.ArrayList;
import java.util.List;

import framework.api.query.services.PagingResult;
import framework.sqlclient.api.free.AbstractNativeQuery;

/**
 * A pager.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Pager {
	
	/**
	 * @return the Pager
	 */
	public static Pager create(){
		return new Pager();
	}
	
	/**
	 * Private constructor
	 */
	private Pager(){
		
	}
	
	/**
	 * Prepares the paging data.
	 * Only can be called in first search. 
	 * 
	 * @param query the query
	 * @param startPosition the startPosition
	 * @param pageSize the pageSize
	 * @return the result
	 */
	public PagingResult prepare(AbstractNativeQuery query, int startPosition , int pageSize){
		return paging(query,startPosition,pageSize,query.count());
	}

	/**
	 * Gets the paging data.
	 * 
	 * @param query the query
	 * @param startPosition the startPosition
	 * @param pageSize the pageSize
	 * @param totalCount the totalCount
	 * @return the result
	 */
	public PagingResult paging(AbstractNativeQuery query, int startPosition , int pageSize, int totalCount){

		if( pageSize <= 0){
			throw new IllegalArgumentException("'pageSize' must be over 0 ");
		}
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

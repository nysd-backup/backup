/**
 * Copyright 2011 the original author
 */
package framework.api.query.services;

import java.io.Serializable;
import java.util.List;


/**
 * The result of paging.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class PagingResult implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** the current page's data */
	private List currentPageData;
	
	/** the current page's no */
	private int currentPageNo;
	
	/** the total count hit */
	private int totalCount;
	
	/**
	 * @param currentPageData　the current page data
	 * @param currentPageNo the current page no
	 * @param totalCount the total count
	 */
	public PagingResult(List currentPageData,int currentPageNo,int totalCount){
		this.currentPageData = currentPageData;
		this.currentPageNo = currentPageNo;
		this.totalCount = totalCount;
	}

	/**
	 * @return the total count
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @return the current page no
	 */
	public int getCurrentPageNo() {
		return this.currentPageNo;
	}

	/**
	 * @return the current page data
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getCurrentPageData() {
		return (List<T>)this.currentPageData;
	}

}

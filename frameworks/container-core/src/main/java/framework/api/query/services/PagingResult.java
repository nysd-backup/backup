/**
 * Copyright 2011 the original author
 */
package framework.api.query.services;

import java.io.Serializable;
import java.util.List;


/**
 * ページングデータ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class PagingResult implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 現在ページのデータ */
	private List currentPageData;
	
	/** 現在ページ番号 */
	private int currentPageNo;
	
	/** 総件数 */
	private int totalCount;
	
	/**
	 * @param currentPageData　現在ページのデータ 
	 * @param currentPageNo 現在ページ番号
	 * @param totalCount 総件数
	 */
	public PagingResult(List currentPageData,int currentPageNo,int totalCount){
		this.currentPageData = currentPageData;
		this.currentPageNo = currentPageNo;
		this.totalCount = totalCount;
	}

	/**
	 * @return 総件数
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @return 現在ページ番号
	 */
	public int getCurrentPageNo() {
		return this.currentPageNo;
	}

	/**
	 * @return 現在ページデータ
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getCurrentPageData() {
		return (List<T>)this.currentPageData;
	}

}

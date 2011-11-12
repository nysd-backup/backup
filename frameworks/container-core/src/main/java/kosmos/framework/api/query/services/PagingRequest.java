/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.services;

import java.io.Serializable;


/**
 * ページング用リクエストパラメタ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PagingRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	/** ページサイズ */
	private int pageSize = 0;
	
	/** ポジション */
	private int startPosition = 0;
	
	/** 総件数 */
	private int totalCount = 0;
	
	/** リクエスト */
	private QueryRequest internal;
	
	/**
	 * @return ページサイズ
	 */
	public int getPageSize(){
		return pageSize;
	}
	
	/**
	 * @param pageSize ページサイズ
	 */
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}

	/**
	 * @param internal the internal to set
	 */
	public void setInternal(QueryRequest internal) {
		this.internal = internal;
	}

	/**
	 * @return the request
	 */
	public QueryRequest getInternal() {
		return internal;
	}

	/**
	 * @param startPosition the startPosition to set
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * @return the startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
}

/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query.paging;

/**
 * ページングコンテキストファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PagingContexFactory {

	/**
	 * @return コンテキスト
	 */
	public PagingContext create();
}

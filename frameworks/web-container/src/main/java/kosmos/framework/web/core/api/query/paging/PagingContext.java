package kosmos.framework.web.core.api.query.paging;

import java.util.List;

import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;

/**
 * The paging context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public interface PagingContext {

	/**
	 * @return the current page data
	 */
	public List getCurrentPageData();

	/**
	 * @return the total count
	 */
	public int getTotalCount();

	/**
	 * @return the total page count
	 */
	public int getTotalPage();

	/**
	 * @return the current page no(1～)
	 */
	public int getCurrentPageNo();

	/**
	 * Prepares the paging.
	 * Call first to do pagding.
	 * 
	 * @param query the query
	 * @param pageSize the one page size 
	 */
	public List prepare(AbstractNativeQuery query, int pageSize);

	/**
	 * Gets the specifed page's data.
	 * 
	 * @param pageNo the target page no
	 * @return the data 
	 */
	public List getPageData(int pageNo);

}

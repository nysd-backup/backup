/**
 * Use is subject to license terms.
 */
package framework.service.ext.services;

import javax.ejb.Stateless;

import framework.api.query.services.PagingService;
import framework.service.core.services.AbstractLatestPagingService;
import framework.service.ext.locator.ServiceLocatorImpl;
import framework.sqlclient.api.free.QueryFactory;

/**
 * 最新取得用ページングサービス.
 *
 * @author yoshida-n
 * @version	2011/05/13 created.
 */
@Stateless
public class LatestPagingServiceImpl extends AbstractLatestPagingService implements PagingService{
	
	/**
	 * @see framework.service.core.services.AbstractLatestPagingService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createWebQueryFactory();
	}
}
/**
 * Use is subject to license terms.
 */
package framework.service.ext.services;

import javax.ejb.Stateless;

import framework.api.query.services.NativeQueryService;
import framework.service.core.services.AbstractNativeQueryService;
import framework.service.ext.locator.ServiceLocatorImpl;
import framework.sqlclient.api.free.QueryFactory;

/**
 * リモートからのNativeQuery実行.
 *
 * @author yoshida-n
 * @version	2011/05/13 created.
 */
@Stateless
public class NativeQueryServiceImpl extends AbstractNativeQueryService implements NativeQueryService{

	/**
	 * @see framework.service.core.services.AbstractNativeQueryService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createWebQueryFactory();
	}
	

}

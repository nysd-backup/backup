/**
 * Use is subject to license terms.
 */
package framework.web.core.api.query;

import framework.api.query.services.NativeQueryService;
import framework.core.exception.PoorImplementationException;
import framework.logics.utils.ClassUtils;
import framework.sqlclient.api.free.AbstractFreeQuery;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.AbstractUpdate;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.QueryAccessor;
import framework.sqlclient.api.free.QueryFactory;
import framework.web.core.api.service.ServiceCallable;
import framework.web.core.api.service.ServiceFacade;

/**
 * WEB用のネイティブクエリファクトリ.
 *
 * @author yoshida-n
 * @version	2011/06/13 created.
 */
@ServiceCallable
public class WebNativeQueryFactory implements QueryFactory{
	
	@ServiceFacade
	private NativeQueryService service;
	

	/**
	 * @see framework.sqlclient.api.free.sql.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery, T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass) {
		K delegate = null;
		
		if(AbstractNativeQuery.class.isAssignableFrom(queryClass)){			
			delegate = (K)new WebNativeQueryEngine(queryClass,service);
		}else{
			throw new PoorImplementationException("unsupporetd query type : type = " + queryClass);
		}
		T eq = ClassUtils.newInstance(queryClass);
		QueryAccessor.setDelegate(eq, delegate);		
		return eq;
	}

	/**
	 * @see framework.sqlclient.api.free.sql.QueryFactory#createUpdate(java.lang.Class)
	 */
	@Override
	public <K extends FreeUpdate, T extends AbstractUpdate<K>> T createUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

}

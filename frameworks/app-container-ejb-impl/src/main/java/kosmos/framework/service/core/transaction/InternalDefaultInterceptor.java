/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

/**
 * The default interceptor.
 * 
 * @author yoshida-n
 * @version	created.
 */
public class InternalDefaultInterceptor extends kosmos.framework.service.core.advice.InternalDefaultInterceptor{
	
	@Resource
	private SessionContext sessionContext;

	/**
	 * @see kosmos.framework.service.core.transaction.InternalDefaultInterceptor#afterError(java.lang.Object)
	 */
	@Override
	protected void afterError(Object retValue) {
		sessionContext.setRollbackOnly();
	}
}

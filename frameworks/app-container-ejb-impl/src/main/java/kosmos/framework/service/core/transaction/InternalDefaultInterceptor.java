/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.service.core.exception.ApplicationException;

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
	protected BusinessException afterError(Object retValue) {
		sessionContext.setRollbackOnly();
		return new ApplicationException(null);
	}
}

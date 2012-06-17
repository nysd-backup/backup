/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.MDC;

import service.framework.core.activation.ServiceLocator;
import core.base.AbstractRequest;
import core.base.AbstractResponse;
import core.exception.BusinessException;
import core.message.MessageResult;



/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextControllableTransactionInterceptor {
	
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
	
		ServiceContext context = ServiceContext.getCurrentInstance();
		
		if(context == null){
			return invokeAtTopLevel(ic);
		}else {
			return invoke(ic);
		}

	}
	
	/**
	* Process at the except top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invoke(InvocationContext ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(InvocationContext ic) throws Throwable {
	
		ServiceContext context = ServiceLocator.createDefaultServiceContext();
		context.initialize();
		try{
			AbstractRequest request = (AbstractRequest)ic.getParameters()[0];
			MDC.put("requestId", request.getRequestId());			
			context.setRequestId(request.getRequestId());
			
			AbstractResponse returnValue = (AbstractResponse)proceed(ic);			
			if(context.getCurrentUnitOfWork().isRollbackOnly()){
				sessionContext.setRollbackOnly();
				returnValue.setFail(true);
			}	
		
			returnValue.setMessageResult(context.getMessageList());				
			
			return returnValue;
		}catch(BusinessException be){
			be.setMessageList(context.getMessageList().toArray(new MessageResult[0]));
			throw be;
		}finally{			
			context.release();
			MDC.remove("requestId");
		}
	}
	
	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object proceed(InvocationContext ic) throws Throwable{		
		return ic.proceed();
		
	}
}

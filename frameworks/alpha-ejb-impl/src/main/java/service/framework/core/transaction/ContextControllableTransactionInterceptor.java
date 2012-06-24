/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
	private EJBContext sessionContext;
	
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
	 * Starts new unit of work if in transaction border.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invoke(InvocationContext ic) throws Throwable {
		ServiceContextImpl context = (ServiceContextImpl)ServiceContext.getCurrentInstance();
		boolean isTransactionBorder = false;
		TransactionAttribute attribute = ic.getMethod().getDeclaringClass().getAnnotation(TransactionAttribute.class);
				
		if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
			isTransactionBorder = true;
		}else {
			attribute = ic.getMethod().getAnnotation(TransactionAttribute.class);
			if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
				isTransactionBorder = true;
			}
		}
		if(isTransactionBorder){
			context.startUnitOfWork();
		}
		try{		
			return ic.proceed();
		}finally{			
			//Synchronization 
			if(sessionContext.getRollbackOnly()){
				context.setRollbackOnly();
			}
			if(isTransactionBorder){
				if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
					sessionContext.setRollbackOnly();				
				}	
				context.endUnitOfWork();
			}
		}
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(InvocationContext ic) throws Throwable {
	
		ServiceContextImpl context = (ServiceContextImpl)ServiceLocator.createDefaultServiceContext();		
		try{
			context.initialize();
			//トランザクション開始
			context.startUnitOfWork();
			if(ic.getParameters() != null && ic.getParameters()[0] instanceof AbstractRequest){
				AbstractRequest request = (AbstractRequest)ic.getParameters()[0];
				MDC.put("requestId", request.getRequestId());			
				context.setRequestId(request.getRequestId());
			}
			Object returnValue = proceed(ic);			
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}	
			if(returnValue instanceof AbstractResponse){
				((AbstractResponse)returnValue).setFail(sessionContext.getRollbackOnly());
				((AbstractResponse)returnValue).setMessageResult(context.getMessageList());				
			}
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

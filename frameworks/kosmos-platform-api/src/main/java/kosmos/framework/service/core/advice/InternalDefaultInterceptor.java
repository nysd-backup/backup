/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.core.exception.SystemException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.logics.log.LogWriter;
import kosmos.framework.core.logics.log.LogWriterFactory;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.TransactionManagingContext;

/**
 * The default interceptor.
 * 
 * <pre>
 * Creates the context and throws the BusinessException if the service is failed.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalDefaultInterceptor implements InternalInterceptor {
	
	private static final LogWriter LOG = LogWriterFactory.getLog(InternalDefaultInterceptor.class);

	/**
	 * @see kosmos.framework.service.core.advice.InternalInterceptor#around(kosmos.framework.core.activation.InvocationAdapter)
	 */
	@Override
	public Object around(InvocationAdapter ic) throws Throwable {
		
		ServiceContext context = ServiceContext.getCurrentInstance();
		if(context == null){
			throw new PoorImplementationException("context is required");
		}
		
		if(context.isTopLevel()){
			context.setTopLevel(false);
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
	protected Object invoke(InvocationAdapter ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(InvocationAdapter ic) throws Throwable {
	
		TransactionManagingContext context = (TransactionManagingContext)TransactionManagingContext.getCurrentInstance();
	
		Throwable throwable = null;
		Object returnValue = null;
		
		try{
			returnValue = proceed(ic);	
		}catch(Throwable t){
			LOG.error(t.getMessage(),t);
			throwable = t;
		}
		if(throwable != null){		
			afterThrowable(throwable,context);
			throw throwable;
		}else{			
			afterProceed(context);
			return returnValue;
		}
	
	}
	
	/**
	 * After success.
	 * @param context　the context
	 */
	protected void afterProceed(TransactionManagingContext context){
		//Springの場合はTransactionInterceptorでトランザクション境界毎にロールバックフラグを立てるのでここの処理は必要なし
		//EJBの場合にはSessionContext#setRollbakOnlyが必要
	}
	
	/**
	 * After error.
	 * @param context　the context
	 */
	protected void afterThrowable(Throwable throwable,TransactionManagingContext context){
		
		try{
			FaultNotifier faultNotifier = ServiceLocator.createDefaultFaultNotifier();
			//障害通知
			if(throwable instanceof SystemException){
				SystemException se = SystemException.class.cast(throwable);
				MessageBean bean = new MessageBean(se.getMessageId(),se.getArgs());
				MessageResult message = ServiceLocator.createDefaultMessageBuilder().load(bean);
			//TODO	if(message.isNotifyTarget()){
					faultNotifier.notify(message.getCode(), message.getMessage(), message.getLevel());
			//	}
			}
		}catch(Throwable t){
			LOG.error(t.getMessage(),t);
		}
	}
	
	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object proceed(InvocationAdapter ic) throws Throwable{
		TransactionManagingContext context = (TransactionManagingContext)TransactionManagingContext.getCurrentInstance();
		context.pushCallStack();
		try{
			return ic.proceed();
		}finally{
			context.popCallStack();
		}
	}

}

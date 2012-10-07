/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.simple;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.InvocationContext;

import org.apache.log4j.MDC;

import alpha.framework.core.base.CorrelativeRequest;
import alpha.framework.core.base.CorrelativeResponse;
import alpha.framework.core.exception.BusinessException;
import alpha.framework.core.message.Message;
import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.advice.InvocationAdapterImpl;
import alpha.framework.domain.transaction.AbstractServiceInterceptor;
import alpha.framework.domain.transaction.ServiceContext;


/**
 * SimpleInterceptor.
 * <pre>
 * Don't add the message in autonomous transaction.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class SimpleInterceptor extends AbstractServiceInterceptor{

	@Resource
	private EJBContext sessionContext;

	/**
	 * @see alpha.framework.domain.transaction.AbstractServiceInterceptor#invokeRoot(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invokeRoot(InvocationContext ic) throws Throwable {
		ServiceContext context = createServiceContext();
		try{		
			initialize(ic);
			context.initialize();						
			Object returnValue = proceed(ic);
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return editResponse( returnValue, context);
		}catch(RuntimeException be){
			return handleException(be,context);
		}finally{				
			context.release();		
			terminate();
		}
	}
	
	/**
	 * Initialize the transaction.
	 * @param ic
	 */
	protected void initialize(InvocationContext ic){
		if(ic.getParameters() != null && ic.getParameters().length > 0 && ic.getParameters()[0] instanceof CorrelativeRequest){
			CorrelativeRequest request = (CorrelativeRequest)ic.getParameters()[0];
			MDC.put("correlationId", request.getCorrelationId());			
		}
	}
	
	/**
	 * Terminate the Transaction
	 */
	public void terminate() {
		MDC.remove("correlationId");
	}
	
	/**
	 * Handles the exception
	 * @param e
	 * @param context
	 * @return
	 */
	protected Object handleException(RuntimeException e,ServiceContext context){
		if(e instanceof BusinessException ){
			BusinessException be = (BusinessException)e;
			List<Message> result = be.getMessageList();
			if(result != null && !result.isEmpty()){
				result.addAll(context.getMessageList());
			}else{
				be.setMessageList(context.getMessageList());
			}
			throw be;
		}else{
			throw e;
		}
		
	}
	
	/**
	 * Edits the response.
	 * @param invocationSource the source
	 * @param returnValue the returnValue
	 * @param context the context
	 * @return the edited returnValue
	 */
	protected Object editResponse(Object returnValue, ServiceContext context){
		
		if(returnValue != null && returnValue instanceof CorrelativeResponse){
			CorrelativeResponse retValue = (CorrelativeResponse)returnValue;
			retValue.setMessages(context.getMessageList());
			retValue.setFailed(context.isFailed());		
		}

		return returnValue;
	}
	
	/**
	 * @return the alpha.domain context
	 */
	protected ServiceContext createServiceContext(){
		return new SimpleServiceContextImpl();
	}

	/**
	 * @see alpha.framework.domain.transaction.AbstractServiceInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	protected Object proceed(InvocationContext ic) throws Throwable{
		return new InternalPerfInterceptor().around(new InvocationAdapterImpl(ic));
	}

}

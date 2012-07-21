/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction.noautonomous;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.InvocationContext;

import service.framework.core.transaction.AbstractServiceInterceptor;
import service.framework.core.transaction.MessageContext;
import service.framework.core.transaction.ServiceContext;
import core.base.AbstractRequest;
import core.base.AbstractResponse;
import core.exception.BusinessException;
import core.message.MessageResult;

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
	
	/** called by EJB local call under this */
	public static final int INVOCATION_EJB = 0;
	
	/** called by RS client */
	public static final int INVOCATION_RS = 1;
	
	/** called by WS client */
	public static final int INVOCATION_WS = 2;
	
	/** called by MDB */
	public static final int INVOCATION_MDB = 3;	

	@Resource
	private EJBContext sessionContext;

	/**
	 * @see service.framework.core.transaction.AbstractServiceInterceptor#invokeFirst(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invokeFirst(InvocationContext ic) throws Throwable {
		ServiceContext context = createServiceContext();
		try{			
			int invocationSource = getInvocationSource(ic.getParameters());			
			context.initialize();						
			Object returnValue = ic.proceed();		
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return editResponse(invocationSource, returnValue, context);
		}catch(BusinessException be){
			List<MessageResult> result = be.getMessageList();
			if(result != null && !result.isEmpty()){
				result.addAll(context.getMessageList());
			}else{
				be.setMessageList(context.getMessageList());
			}
			throw be;
		}finally{				
			context.release();					
		}
	}
	
	/**
	 * Edits the response.
	 * @param invocationSource the source
	 * @param returnValue the returnValue
	 * @param context the context
	 * @return the edited returnValue
	 */
	protected Object editResponse(int invocationSource , Object returnValue, ServiceContext context){
		//EJB local call
		if(invocationSource <= INVOCATION_EJB){
			if(returnValue != null && returnValue instanceof AbstractResponse){
				AbstractResponse retValue = (AbstractResponse)returnValue;
				retValue.setMessageResult(context.getMessageList());
				retValue.setFail(context.hasErrorMessage());		
			}
		//Web Service or Message Driven Call	
		}else {
			if(!context.getMessageList().isEmpty()){
				new MessageContext(context.getMessageList());			
			}
		}
		return returnValue;
	}
	
	/**
	 * Gets the invocation source
	 * @param request the request
	 * @return the invocation source
	 */
	protected int getInvocationSource(Object[] request){
		if(request != null && request.length > 0 && request[0] instanceof AbstractRequest){
			AbstractRequest req = (AbstractRequest)request[0];
			return req.getInvocationSource();
		}else {
			return INVOCATION_EJB;
		}
	}
	
	/**
	 * @return the service context
	 */
	protected ServiceContext createServiceContext(){
		return new SimpleServiceContextImpl();
	}

	/**
	 * @see service.framework.core.transaction.AbstractServiceInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		return ic.proceed();
	}

}

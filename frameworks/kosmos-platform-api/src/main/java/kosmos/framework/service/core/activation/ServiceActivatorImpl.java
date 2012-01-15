/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.dto.CompositeReply;
import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.service.core.transaction.TransactionManagingContext;


/**
 * Activates the services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceActivatorImpl implements ServiceActivator{

	/**
	 * @see kosmos.framework.core.activation.ServiceActivator#activateAndInvoke(kosmos.framework.core.dto.CompositeRequest)
	 */
	@Override
	public CompositeReply activate(CompositeRequest dto) throws Throwable{
		
		Object service = getService(dto);	
		
		Method m = null;
		if(dto.getParameterTypeNames() == null){
			m = service.getClass().getMethod(dto.getMethodName());
		}else {
			Class<?>[] clss = new Class[dto.getParameterTypeNames().length];
			for(int i = 0 ; i< clss.length; i++){
				clss[i] = Class.forName(dto.getParameterTypeNames()[i]);
			}
			m = service.getClass().getMethod(dto.getMethodName(),clss);
		}
		TransactionManagingContext context = ServiceLocator.createDefaultServiceContext();
		context.initialize();
		try{
			
			Object value =  m.invoke(service, (Object[])dto.getParameter());
			CompositeReply reply = new CompositeReply();
			reply.setMessageList(context.getMessageArray());
			reply.setData((Serializable)value);
			return reply;
			
		}catch(InvocationTargetException ite){
			setMessageToException(ite.getTargetException(),context);
			throw ite.getTargetException();
		}catch(BusinessException be){
			setMessageToException(be,context);
			throw be;
		}finally{
			context.release();
		}
	}

	
	/**
	 * Set the message to the exception.
	 * @param throwable
	 * @param context
	 */
	private void setMessageToException(Throwable throwable, TransactionManagingContext context){
		//Exceptionがスローされた場合はException内にメッセージを追加する
		if(throwable instanceof BusinessException){
			BusinessException.class.cast(throwable).setMessageList(context.getMessageArray());
		}
	}
	
	/**
	 * Gets the service.
	 * 
	 * @param dto the DTO
	 * @return the service
	 */
	protected Object getService(CompositeRequest dto){
		return ServiceLocator.lookup(dto.getServiceName());
	}

}

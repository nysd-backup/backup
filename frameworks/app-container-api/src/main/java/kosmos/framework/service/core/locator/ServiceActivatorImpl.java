/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kosmos.framework.api.dto.ReplyDto;
import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.service.core.advice.InvocationAdapter;


/**
 * Activates the services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceActivatorImpl implements ServiceActivator{

	/**
	 * @see kosmos.framework.api.service.ServiceActivator#activate(kosmos.framework.api.dto.RequestDto)
	 */
	@Override
	public ReplyDto activate(RequestDto dto){
	
		
		ReplyDto reply = new ReplyDto();
		try {
			reply.setRetValue(proceed(dto));	
		} catch (Throwable t) {
			if(t instanceof RuntimeException ){
				throw (RuntimeException)t;
			}else if(t instanceof InvocationTargetException){
				InvocationTargetException ite = (InvocationTargetException)t;
				if(ite.getTargetException() instanceof RuntimeException){
					throw (RuntimeException)ite.getTargetException();
				}else if( ite.getTargetException() instanceof Error){
					throw (Error)ite.getTargetException();
				}
				throw new IllegalStateException(ite.getTargetException());
			}else if( t instanceof Error){
				throw (Error)t;
			}else {
				throw new IllegalStateException(t);
			}
		}		
		return reply;
		
	}
	
	/**
	 * Proceed the service.
	 * 
	 * @param dto the dto
	 * @return the adapter
	 */
	private Object proceed(final RequestDto dto) throws Throwable{
		InvocationAdapter adapter = new InvocationAdapter() {
			
			@Override
			public Object proceed() throws Throwable {			
				Object service = getThis();
				Method m = dto.getTargetClass().getMethod(dto.getMethodName(), dto.getParameterTypes());
				return m.invoke(service, (Object[])dto.getParameter());
			}
			
			@Override
			public Object getThis() {
				return getService(dto);
			}
			
			@Override
			public String getMethodName() {
				return dto.getMethodName();
			}
			
			@Override
			public String getDeclaringTypeName() {
				return dto.getTargetClass().getName();
			}
			
			@Override
			public Object[] getArgs() {
				return dto.getParameter();
			}
		};
		
		return new ServiceFrontEnd().invoke(adapter);
	}
	
	/**
	 * Gets the service.
	 * 
	 * @param dto the DTO
	 * @return the service
	 */
	protected Object getService(RequestDto dto){
		if(dto.getAlias() != null){
			return ServiceLocator.lookup(dto.getAlias());
		}else{
			return ServiceLocator.lookupByInterface(dto.getTargetClass());
		}
	}

}

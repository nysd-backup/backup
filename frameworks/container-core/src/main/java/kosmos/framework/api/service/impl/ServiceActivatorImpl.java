/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ComponentLocator;
import kosmos.framework.api.service.ServiceActivator;


/**
 * Activates the services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceActivatorImpl implements ServiceActivator{

	/**
	 * @see kosmos.framework.api.service.ServiceActivator#activateAndInvoke(kosmos.framework.api.dto.RequestDto)
	 */
	@Override
	public Object activateAndInvoke(RequestDto dto){
		
		Object service = getService(dto);
		
		try{
			
			Method m = dto.getTargetClass().getMethod(dto.getMethodName(), dto.getParameterTypes());
			return m.invoke(service, (Object[])dto.getParameter());
			
		}catch(Throwable t){
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
	}
	
	/**
	 * Gets the service.
	 * 
	 * @param dto the DTO
	 * @return the service
	 */
	protected Object getService(RequestDto dto){
		if(dto.getAlias() != null){
			return ComponentLocator.lookup(dto.getAlias());
		}else{
			return ComponentLocator.lookupByInterface(dto.getTargetClass());
		}
	}

}

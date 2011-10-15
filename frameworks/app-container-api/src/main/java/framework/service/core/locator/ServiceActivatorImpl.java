/**
 * Copyright 2011 the original author
 */
package framework.service.core.locator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import framework.api.dto.RequestDto;
import framework.api.service.ServiceActivator;

/**
 * Activates the services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceActivatorImpl implements ServiceActivator{

	/**
	 * @see framework.api.service.ServiceActivator#activate(framework.api.dto.RequestDto)
	 */
	@Override
	public Object activate(RequestDto dto){
	
		Object service = getService(dto);
		Method m;
		try {
			m = dto.getTargetClass().getMethod(dto.getMethodName(), dto.getParameterTypes());
			return m.invoke(service, (Object[])dto.getParameter());	
		} catch (RuntimeException e) {
			throw e;
		} catch (InvocationTargetException e){
			if(e.getTargetException() instanceof RuntimeException){
				throw (RuntimeException)e.getTargetException();
			}
			throw new IllegalStateException(e.getTargetException());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}			
		
	}
	
	/**
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

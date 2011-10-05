/**
 * Copyright 2011 the original author
 */
package framework.service.core.locator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import framework.api.dto.RequestDto;
import framework.api.service.DelegatingServiceInvoker;

/**
 * A 'BusinessDelegate' at the service layer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class DelegatingServiceInvokerImpl implements DelegatingServiceInvoker{

	/**
	 * @see framework.api.service.DelegatingServiceInvoker#processService(framework.api.dto.RequestDto)
	 */
	@Override
	public Object processService(RequestDto dto){
	
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

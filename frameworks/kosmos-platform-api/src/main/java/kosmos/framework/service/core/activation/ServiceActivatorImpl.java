/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.dto.RequestDto;


/**
 * Activates the services.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceActivatorImpl implements ServiceActivator{

	/**
	 * @see kosmos.framework.core.activation.ServiceActivator#activateAndInvoke(kosmos.framework.core.dto.RequestDto)
	 */
	@Override
	public Object activateAndInvoke(RequestDto dto) throws Throwable{
		
		Object service = getService(dto);	
		Method m = dto.getTargetClass().getMethod(dto.getMethodName(), dto.getParameterTypes());
		try{
			return m.invoke(service, (Object[])dto.getParameter());
		}catch(InvocationTargetException ite){
			throw ite.getTargetException();
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
			return ServiceLocator.lookup(dto.getAlias());
		}else{
			return ServiceLocator.lookupByInterface(dto.getTargetClass());
		}
	}

}

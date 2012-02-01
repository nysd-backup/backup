/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.Method;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.dto.CompositeRequest;


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
	public Object activate(CompositeRequest dto) throws Throwable{
		
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
		return  m.invoke(service, (Object[])dto.getParameter());
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

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.service;

import java.io.Serializable;
import java.lang.reflect.Method;

import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ServiceActivator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * The BusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultBusinessDelegate implements BusinessDelegate,ApplicationContextAware{

	private String alias = null;
	
	private ApplicationContext context = null;
	
	/**
	 * @see kosmos.framework.web.core.api.service.BusinessDelegate#setAlias(java.lang.String)
	 */
	public void setAlias(String alias){
		this.alias = alias;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	
		Serializable[] serial = null;
		if( args == null){
			serial = new Serializable[0];
		}else{
			serial = new Serializable[args.length];
			for(int i = 0 ; i < args.length; i++){
				serial[i] = Serializable.class.cast(args[i]);
			}
		}
			
		RequestDto dto = new RequestDto();
		dto.setAlias(alias);
		dto.setTargetClass(method.getDeclaringClass());
		dto.setMethodName(method.getName());
		dto.setParameter(serial);
		dto.setParameterTypes(method.getParameterTypes());
		
		return processService(dto);
		
	}
	
	/**
	 * Invokes the service
	 * @param dto DTO
	 * @return the reply
	 */
	protected Object processService(RequestDto dto){
		return context.getBean(ServiceActivator.class).activateAndInvoke(dto);
	}

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

}

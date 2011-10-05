/**
 * Copyright 2011 the original author
 */
package framework.service.core.messaging;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import framework.api.dto.RequestDto;

/**
 * the message producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractMessageProducer implements InvocationHandler{
	
	/** the selector for JMS destination */
	private DestinationSelector destinationSelector;
	
	/**
	 * @param selecter the selector to set
	 */
	public void setDestinationSelector(DestinationSelector selector){
		this.destinationSelector = selector;
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
		dto.setAlias(null);
		dto.setTargetClass(method.getDeclaringClass());
		dto.setMethodName(method.getName());
		dto.setParameter(serial);
		dto.setParameterTypes(method.getParameterTypes());
		
		
		//宛先生成
		String dst = null;
		if(destinationSelector != null){
			dst = destinationSelector.createDestinationName(method);
		}
	
		return invoke(dto,dst);
	}
	
	/**
	 * invoke service.
	 * @param dto the DTO
	 * @param destinationName the name
	 * @return the result
	 * @throws Throwable　any error
	 */
	protected abstract Object invoke(RequestDto dto ,String destinationName) throws Throwable;
}

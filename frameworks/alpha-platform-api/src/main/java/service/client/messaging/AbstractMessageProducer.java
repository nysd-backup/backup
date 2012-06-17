/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



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
		InvocationParameter dto = new InvocationParameter();
		dto.setServiceName(method.getDeclaringClass().getName());		
		dto.setMethodName(method.getName());
		dto.setParameter(serial);
		Class<?>[] clss = method.getParameterTypes();
		if( clss != null){			
			String[] names = new String[clss.length];
			for(int i = 0 ; i < names.length; i++){
				names[i] = clss[i].getName();
			}
			dto.setParameterTypeNames(names);
		}
		
		//宛先生成
		String dst = String.format("%s.%s", method.getDeclaringClass().getName(),method.getName());
		if(destinationSelector != null){
			dst = destinationSelector.createDestinationName(method,serial);
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
	protected abstract Object invoke(InvocationParameter dto ,String destinationName) throws Throwable;
}

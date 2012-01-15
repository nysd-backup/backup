/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.service;

import java.io.Serializable;
import java.lang.reflect.Method;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.context.AbstractContainerContext;
import kosmos.framework.core.dto.CompositeReply;
import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.message.MessageResult;


/**
 * The BusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultBusinessDelegate implements BusinessDelegate{

	private String alias = null;
	
	private ServiceActivator serviceActivator = null;
	
	/**
	 * @see kosmos.framework.client.service.BusinessDelegate#setAlias(java.lang.String)
	 */
	public void setAlias(String alias){
		this.alias = alias;
	}
	
	/**
	 * @param serviceActivator the serviceActivator to set
	 */
	public void setServiceActivator(ServiceActivator serviceActivator){
		this.serviceActivator = serviceActivator;
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
			
		CompositeRequest dto = new CompositeRequest();
		if(alias != null){	
			dto.setServiceName(alias);
		}else{
			dto.setServiceName(method.getDeclaringClass().getName());
		}
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
		CompositeReply reply = null;
		try{
			reply = processService(dto);
			//メッセージをクライアントコンテキストに追加
			for(MessageResult message: reply.getMessageList()){
				AbstractContainerContext.getCurrentInstance().addMessage(message);
			}
		}catch(BusinessException be){
			//メッセージをクライアントコンテキストに追加
			for(MessageResult message: be.getMessageList()){
				AbstractContainerContext.getCurrentInstance().addMessage(message);
			}
		}
		return reply.getData();
		
	}
	
	/**
	 * Invokes the service
	 * @param dto DTO
	 * @return the reply
	 */
	protected CompositeReply processService(CompositeRequest dto) throws Throwable{
		return serviceActivator.activate(dto);
	}

}

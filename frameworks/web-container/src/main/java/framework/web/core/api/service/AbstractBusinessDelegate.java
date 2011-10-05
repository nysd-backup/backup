/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import java.io.Serializable;
import java.lang.reflect.Method;

import framework.api.dto.ReplyDto;
import framework.api.dto.ReplyMessage;
import framework.api.dto.RequestDto;
import framework.web.core.context.WebContext;

/**
 * The BusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractBusinessDelegate implements BusinessDelegate{

	private String alias = null;
	
	/**
	 * @see framework.web.core.api.service.BusinessDelegate#setAlias(java.lang.String)
	 */
	public void setAlias(String alias){
		this.alias = alias;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		WebContext context = WebContext.getCurrentInstance();	
		
		Serializable[] serial = null;
		if( args == null){
			serial = new Serializable[0];
		}else{
			serial = new Serializable[args.length];
			for(int i = 0 ; i < args.length; i++){
				serial[i] = Serializable.class.cast(args[i]);
			}
		}
			
		RequestDto dto  = new RequestDto();
		dto.setAlias(alias);
		dto.setTargetClass(method.getDeclaringClass());
		dto.setMethodName(method.getName());
		dto.setParameter(serial);
		dto.setParameterTypes(method.getParameterTypes());
		
		ReplyDto reply = processService(dto);
		
		//メッセージがある場合はメッセージを追加
		if( context != null){
			ReplyMessage[] messageList = reply.getMessages();
			if(messageList != null){
				for(ReplyMessage message : messageList){	
					context.addMessage(message);
				}
			}
		}
		return reply.getReply();
	}
	
	/**
	 * Invokes the service
	 * @param dto DTO
	 * @return the reply
	 */
	protected abstract ReplyDto processService(RequestDto dto);

}

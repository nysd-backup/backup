/**
 * Copyright 2011 the original author
 */
package framework.service.core.transaction;

import framework.api.dto.ClientRequestBean;
import framework.api.dto.ClientSessionBean;
import framework.core.context.AbstractGlobalContext;
import framework.core.message.DefinedMessage;

/**
 * コンテキスト.
 * 1スレッド内のサービス開始/終了間で情報を保持する。
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext extends AbstractGlobalContext{
	
	/** the thread local instance*/
	private static ThreadLocal<ServiceContext> instance = new ThreadLocal<ServiceContext>(){
		protected ServiceContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(ServiceContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @return the current context
	 */
	public static ServiceContext getCurrentInstance(){
		return instance.get();
	}	
	
	/**
	 * initialize the context
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
	}
	
	/**
	 * @param request the request from client
	 */
	public void setClientRequestBean(ClientRequestBean request){
		this.clientRequest = request;
	}
	
	/**
	 * @param session the session between client and server
	 */
	public void setClientSessionBean(ClientSessionBean session){
		this.clientSession = session;
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(DefinedMessage message){
		globalMessageList.add(message);
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	public void release(){
		super.release();		
		setCurrentInstance(null);
	}
}

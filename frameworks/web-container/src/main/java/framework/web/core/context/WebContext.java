/**
 * Copyright 2011 the original author
 */
package framework.web.core.context;

import javax.servlet.http.HttpServletRequest;

import framework.api.dto.ClientSessionBean;
import framework.core.context.AbstractGlobalContext;
import framework.core.message.DefinedMessage;
import framework.core.message.MessageLevel;

/**
 * The context of WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class WebContext extends AbstractGlobalContext{

	/** if ture request is failed */
	protected boolean requestFailed = false;
	
	/** the ThreadLocal */
	private static ThreadLocal<WebContext> instance = new ThreadLocal<WebContext>(){
		protected WebContext initialValue() {
			return null;
		}
	};
	
	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(WebContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @return the current thread's context
	 */
	public static WebContext getCurrentInstance(){
		return instance.get();
	}
	
	/**
	 * @param session the session between the client and the server
	 */
	public void setClientSessionBean(ClientSessionBean session){
		super.clientSession = session;
	}
	
	/**
	 * Set true to requestFailed.
	 */
	public void setRequestFailed(){
		this.requestFailed = true;
	}
	
	/**
	 * @return the requestFailed
	 */
	public boolean isRequestFailed(){
		return requestFailed;
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(DefinedMessage message){
		
		//エラーレベル以上のメッセージはエラー扱い
		if( MessageLevel.Error.getLevel() <= message.getLevel().getLevel()){
			setRequestFailed();
		}
		globalMessageList.add(message);
		
	}


	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	@Override
	public void release(){
		super.release();
		requestFailed = false;
		setCurrentInstance(null);
	}
	
	/**
	 * Initialized the context.
	 * 
	 * @param request the request
	 */
	public abstract void initialize(HttpServletRequest request );
	

}

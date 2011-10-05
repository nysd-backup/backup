/**
 * Copyright 2011 the original author
 */
package framework.web.core.context;

import javax.servlet.http.HttpServletRequest;

import framework.api.dto.ClientRequestBean;
import framework.api.dto.ClientSessionBean;
import framework.api.dto.ReplyMessage;
import framework.core.context.AbstractGlobalContext;
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
	
	private ClientSessionBean clientSessionBean = null;
	
	private ClientRequestBean clientRequestBean = null;
	
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
	 * @param clientSession the clientSession to set
	 */
	public void setClientSessionBean(ClientSessionBean clientSessionBean) {
		this.clientSessionBean = clientSessionBean;
	}

	/**
	 * @return the clientSession
	 */
	public ClientSessionBean getClientSessionBean() {
		return clientSessionBean;
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
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.api.dto.ReplyMessage)
	 */
	@Override
	public void addMessage(ReplyMessage message){
		
		//エラーレベル以上のメッセージはエラー扱い
		if( MessageLevel.Error.getLevel() <= MessageLevel.find(message.getLevel()).getLevel()){
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
		clientSessionBean = null;
		clientRequestBean = null;
		setCurrentInstance(null);
	}
	
	/**
	 * Initialized the context.
	 * 
	 * @param request the request
	 */
	public abstract void initialize(HttpServletRequest request );

	/**
	 * @param clientRequestBean the clientRequestBean to set
	 */
	public void setClientRequestBean(ClientRequestBean clientRequestBean) {
		this.clientRequestBean = clientRequestBean;
	}

	/**
	 * @return the clientRequestBean
	 */
	public ClientRequestBean getClientRequestBean() {
		return clientRequestBean;
	}


	

}

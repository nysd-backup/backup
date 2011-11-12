/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.context;

import javax.servlet.http.HttpServletRequest;

import kosmos.framework.api.dto.ClientRequestBean;
import kosmos.framework.api.dto.ClientSessionBean;
import kosmos.framework.api.dto.ReplyMessage;
import kosmos.framework.core.context.AbstractContainerContext;
import kosmos.framework.core.message.AbstractMessage;
import kosmos.framework.core.message.ErrorMessage;


/**
 * The context of WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class WebContext extends AbstractContainerContext{

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
	 * @see kosmos.framework.core.context.AbstractContainerContext#addMessage(kosmos.framework.core.message.AbstractMessage, java.lang.String)
	 */
	@Override
	public void addMessage(AbstractMessage define,String message){
		//エラーレベル以上のメッセージはエラー扱い
		if(define.getLevel() >= ErrorMessage.LEVEL){
			setRequestFailed();
		}
		ReplyMessage reply = new ReplyMessage();
		reply.setCode(define.getCode());
		reply.setLevel(define.getLevel());
		reply.setMessage(message);
		super.globalMessageList.add(reply);
	}


	/**
	 * @see kosmos.framework.core.context.AbstractContainerContext#release()
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

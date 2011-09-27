/**
 * Copyright 2011 the original author
 */
package framework.web.core.context;

import javax.servlet.http.HttpServletRequest;
import framework.api.dto.ClientSessionBean;
import framework.core.context.AbstractGlobalContext;
import framework.core.message.BuildedMessage;
import framework.core.message.MessageLevel;

/**
 * WebContext.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class WebContext extends AbstractGlobalContext{

	/** 処理失敗を示す */
	protected boolean requestFailed = false;
	
	/** スレッドローカル */
	private static ThreadLocal<WebContext> instance = new ThreadLocal<WebContext>(){
		protected WebContext initialValue() {
			return null;
		}
	};
	
	/**
	 * @param context コンテキスト設定
	 */
	protected static void setCurrentInstance(WebContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @return 現在のコンテキスト
	 */
	public static WebContext getCurrentInstance(){
		return instance.get();
	}
	
	/**
	 * @param session クライアントとのセッション情報
	 */
	public void setClientSessionBean(ClientSessionBean session){
		super.clientSession = session;
	}
	
	/**
	 * 処理失敗を設定する
	 */
	public void setRequestFailed(){
		this.requestFailed = true;
	}
	
	/**
	 * @return true:リクエスト失敗
	 */
	public boolean isRequestFailed(){
		return requestFailed;
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(BuildedMessage message){
		
		//エラーレベル以上のメッセージはエラー扱い
		if( MessageLevel.Error.getLevel() <= message.getDefined().getLevel().getLevel()){
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
	 * コンテキスト初期化
	 * @param request リクエスト
	 */
	public abstract void initialize(HttpServletRequest request );
	

}

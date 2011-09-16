/**
 * Use is subject to license terms.
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
 * @version	2011/05/11 created.
 */
public abstract class WebContext extends AbstractGlobalContext{

	protected boolean processFailed = false;
	
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
	 * @see framework.core.context.AbstractGlobalContext#setRollbackOnly()
	 */
	public void setProcessFailed(){
		this.processFailed = true;
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#isRollbackOnly()
	 */
	public boolean isProcessFailed(){
		return processFailed;
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(BuildedMessage message){
		
		//エラーレベル以上のメッセージはエラー扱い
		if( MessageLevel.Error.getLevel() <= message.getDefined().getLevel().getLevel()){
			setProcessFailed();
		}
		globalMessageList.add(message);
		
	}


	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	@Override
	public void release(){
		super.release();
		processFailed = false;
		setCurrentInstance(null);
	}
	
	/**
	 * コンテキスト初期化
	 */
	public abstract void initialize(HttpServletRequest request );
	

}

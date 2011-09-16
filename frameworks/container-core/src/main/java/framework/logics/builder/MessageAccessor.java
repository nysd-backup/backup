/**
 * Use is subject to license terms.
 */
package framework.logics.builder;

import framework.core.message.MessageBean;


/**
 * メッセージの作成、追加エンジン.
 *
 * @author	yoshida-n
 * @version	2011/02/19 new create
 */
public interface MessageAccessor<T extends MessageBean> {

	/**
	 * メッセージの作成.
	 * 
	 * @param code メッセージコード
	 * @param args 埋め込み文字
	 * @return メッセージ
	 */
	public T createMessage(int code , Object... args);
	

	/**
	 * メッセージの作成.
	 * 
	 * @param message メッセージ
	 */
	public T addMessage(MessageBean message);
	
}

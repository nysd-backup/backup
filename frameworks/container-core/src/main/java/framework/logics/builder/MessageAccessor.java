/**
 * Copyright 2011 the original author
 */
package framework.logics.builder;

import framework.core.message.MessageBean;


/**
 * メッセージの作成、追加エンジン.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
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

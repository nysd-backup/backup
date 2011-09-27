/**
 * Copyright 2011 the original author
 */
package framework.core.context;

import java.util.ArrayList;
import java.util.List;

import framework.api.dto.ClientRequestBean;
import framework.api.dto.ClientSessionBean;
import framework.core.message.BuildedMessage;

/**
 * スレッドローカルコンテキストの基底.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractGlobalContext {

	/** メソッドの深さ */
	protected int callStackLevel = 0;
	
	/** メッセージリスト */
	protected List<BuildedMessage> globalMessageList = new ArrayList<BuildedMessage>();
	
	/** クライアントとのセッション */
	protected ClientSessionBean clientSession = null;
	
	/** クライアントとのリクエストデータ */
	protected ClientRequestBean clientRequest = null;
	
	/**
	 * メッセージを追加する.
	 * エラーレベル以上のメッセージが発生していたらロールバック対象とする。
	 * 
	 * @param message メッセージ
	 */
	public abstract void addMessage(BuildedMessage message);
	
	/**
	 * メソッドネストインクリメント
	 */
	public void pushCallStack(){
		callStackLevel++;
	}
	
	/**
	 * メソッドネストデクリメント
	 */
	public void popCallStack(){
		callStackLevel--;
	}
	
	/**
	 * メソッドネスト
	 */
	public int getCallStackLevel(){
		return callStackLevel;
	}
	
	/**
	 * @return クライアントとのセッションデータ
	 */
	public ClientSessionBean getClientSessionBean(){
		return clientSession;
	}
	
	/**
	 * @return クライアントとのリクエストデータ
	 */
	public ClientRequestBean getClientRequestBean(){
		return clientRequest;
	}
	
	/**
	 * @return メッセージリスト
	 */
	public List<BuildedMessage> getMessageList() {
		return globalMessageList;
	}
	
	/**
	 * リリース
	 */
	protected void release(){
		callStackLevel = 0;
		globalMessageList = new ArrayList<BuildedMessage>();
		clientRequest = null;
		clientSession = null;		
	}

}

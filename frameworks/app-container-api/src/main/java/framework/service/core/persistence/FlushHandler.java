/**
 * Copyright 2011 the original author
 */
package framework.service.core.persistence;

/**
 * 永続化失敗時処理.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface FlushHandler {

	/**
	 * em.flush実行時にPersistenceExceptionが発生した場合にハンドリングを行う。
	 * 例えば、楽観ロックに失敗したら次回以降のflushで同じエラーにならないようrefreshするなど。
	 * 
	 * @param pe　永続化失敗例外
	 */
	public void handle(RuntimeException pe);
}

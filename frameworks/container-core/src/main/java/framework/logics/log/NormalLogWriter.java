/**
 * Copyright 2011 the original author
 */
package framework.logics.log;

/**
 * ログエンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NormalLogWriter {

	/**
	 * @param message デバッグログメッセージ
	 */
	public void debug(String message);

	/**
	 * @param message 情報ログメッセージ
	 */
	public void info(String message);

	/**
	 * @param message トレースログメッセージ
	 */
	public void trace(String message);

	/**
	 * @return デバッグ有効有無
	 */
	public boolean isDebugEnabled();

	/**
	 * @return 情報有効有無
	 */
	public boolean isInfoEnabled();

	/**
	 * @return 証跡有効有無
	 */
	public boolean isTraceEnabled();


}

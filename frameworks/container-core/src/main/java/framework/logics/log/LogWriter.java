/**
 * Use is subject to license terms.
 */
package framework.logics.log;

/**
 * ログエンジン.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public interface LogWriter {

	/** MDCのキー */
	public static enum MDCKey {
		transactionId,
	}

	/**
	 * @param message アクセスログメッセージ
	 */
	public abstract void access(String message);

	/**
	 * @param message デバッグログメッセージ
	 */
	public abstract void debug(String message);

	/**
	 * @param message エラーログメッセージ
	 */
	public abstract void error(String message);

	/**
	 * @param message 情報ログメッセージ
	 */
	public abstract void info(String message);

	/**
	 * @param message トレースログメッセージ
	 */
	public abstract void trace(String message);

	/**
	 * @param message 性能ログメッセージ
	 */
	public abstract void perf(String message);

	/**
	 * @param message エラーメッセージ
	 * @param t エラー
	 */
	public abstract void error(String message, Throwable t);

	/**
	 * @param t 例外
	 */
	public abstract void error(Throwable t);

	/**
	 * @return デバッグ有効有無
	 */
	public abstract boolean isDebugEnabled();

	/**
	 * @return 情報有効有無
	 */
	public abstract boolean isInfoEnabled();

	/**
	 * @param message 警告ログメッセージ
	 */
	public abstract void warn(String message);

	/**
	 * @return 性能ログ有効有無
	 */
	public abstract boolean isPerfEnabled();

	/**
	 * @return 証跡有効有無
	 */
	public abstract boolean isTraceEnabled();

	/**
	 * @return アクセスログ有効有無
	 */
	public abstract boolean isAccessEnabled();

	/**
	 * @param message 警告メッセージ
	 * @param t 例外
	 */
	public abstract void warn(String message, Throwable t);


}
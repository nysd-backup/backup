/**
 * Use is subject to license terms.
 */
package framework.logics.log;

import framework.logics.log.impl.DebugLogWriterImpl;
import framework.logics.log.impl.PerfLogWriterImpl;

/**
 * ログファクトリ.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public final class LogWriterFactory {

	/**
	 * プライベートコンストラクタ
	 */
	private LogWriterFactory(){
	}
	
	/**
	 * @param clazz カテゴリ
	 * @return ライター
	 */
	public static LogWriter getLog(Class<?> clazz) {
		return new DebugLogWriterImpl(clazz);
	}
	
	/**
	 * @param clazz カテゴリ
	 * @return ライター
	 */
	public static NormalLogWriter getPerfLog(Class<?> clazz) {
		return new PerfLogWriterImpl(clazz);
	}
}

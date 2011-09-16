/**
 * Use is subject to license terms.
 */
package framework.service.core.messaging;

/**
 * メッセージ送信Queue/Topicのプリフィクス.
 * 
 * <pre>
 * 例:jms/{$prefix}/....
 * </pre>
 * 
 * @author yoshida-n
 * @version	2011/05/21 created.
 */
public @interface Prefix {

	/** プリフィクス */
	String value();
}

/**
 * Copyright 2011 the original author
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
 * @version 2011/08/31 created.
 */
public @interface Prefix {

	/** プリフィクス */
	String value();
}

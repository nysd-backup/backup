/**
 * Copyright 2011 the original author
 */
package framework.service.core.messaging;

/**
 * メッセージングクライアントファクトリー.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface MessageClientFactory{
	
	/**
	 * Topic送信サービス
	 * @param <T> 型
	 * @param serviceType サービス
	 * @return サービス
	 */
	public <T> T createPublisher(Class<T> serviceType);
	
	/**
	 * Queue送信サービス
	 * @param <T> 型
	 * @param serviceType サービス
	 * @return サービス
	 */
	public <T> T createSender(Class<T> serviceType);
}

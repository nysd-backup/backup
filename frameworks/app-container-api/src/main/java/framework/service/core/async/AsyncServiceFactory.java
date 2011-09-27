/**
 * Copyright 2011 the original author
 */
package framework.service.core.async;

/**
 * 非同期サービスファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AsyncServiceFactory {

	/**
	 * @param <T> 型
	 * @param serviceType サービスタイプ
	 * @return サービス
	 */
	public <T> T create(Class<T> serviceType);
}

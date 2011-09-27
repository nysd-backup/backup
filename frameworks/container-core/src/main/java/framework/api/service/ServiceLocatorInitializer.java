/**
 * Copyright 2011 the original author
 */
package framework.api.service;

/**
 * サービスロケータの初期化.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ServiceLocatorInitializer {
	
	/**
	 * 初期化
	 * @param context コンテキスト
	 */
	public void construct(Object context);
	
	/**
	 * 終了
	 */
	public void destroy();
}

/**
 * Copyright 2011 the original author
 */
package framework.service.core.locator;


/**
 * サービスロケータ.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator {
	
	/** シングルトン */
	protected static ServiceLocator delegate = null;
	
	/**
	 * インターフェースからのサービス取得
	 * @param <T>　型
	 * @param clazz クラス
	 * @return サービス
	 */
	public abstract <T> T lookupServiceByInterface(Class<T> clazz);
	
	/**
	 * サービス取得
	 * @param name クラス
	 * @return サービス
	 */
	public abstract <T> T lookupService(String name);
	
	/**
	 * リモートサービスの取得
	 * @param <T>　型
	 * @param clazz クラス
	 * @return サービス
	 */
	public abstract <T> T lookupRemoteService(Class<T> clazz);
		
	/**
	 * サービス取得
	 * @param <T> 型
	 * @param ifType サービスタイプ
	 * @return サービス
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupServiceByInterface(ifType);
	}
	
	/**
	 * サービス取得
	 * @param <T> 型
	 * @param name サービスタイプ
	 * @return サービス
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupService(name);
	}
	
	
	/**
	 * サービス取得
	 * @param <T> サービス
	 * @param clazz サービスタイプ
	 * @return サービス
	 */
	public static <T> T lookupRemote(Class<T> clazz){
		return delegate.lookupRemoteService(clazz);
	}
	
}

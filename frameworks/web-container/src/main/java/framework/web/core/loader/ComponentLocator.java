/**
 * Use is subject to license terms.
 */
package framework.web.core.loader;


/**
 * WEB層のロケータ.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ComponentLocator {

	/** シングルトン */
	protected static ComponentLocator delegate = null;
		
	/**
	 * デフォルトサービス取得
	 * @param <T>　型
	 * @param clazz クラス
	 * @return サービス
	 */
	public abstract <T> T lookupDefaultService(Class<T> clazz);
	
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
	 * デフォルトで提供されているサービスを取得する
	 * @param <T> 型
	 * @param serviceType サービスタイプ
	 * @return サービス
	 */
	public static <T> T lookupDefault(Class<T> serviceType){
		return delegate.lookupDefaultService(serviceType);
	}
	
	/**
	 * サービス取得
	 * @param <T> サービス
	 * @param serviceType サービスタイプ
	 * @param サービス
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupServiceByInterface(ifType);
	}
	
	/**
	 * サービス取得
	 * @param <T> サービス
	 * @param name サービスタイプ
	 * @param サービス
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupService(name);
	}

}

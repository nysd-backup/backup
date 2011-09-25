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

}

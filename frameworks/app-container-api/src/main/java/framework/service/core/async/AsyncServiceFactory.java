/**
 * Use is subject to license terms.
 */
package framework.service.core.async;

/**
 * 非同期サービスファクトリ.
 *
 * @author yoshida-n
 * @version	2011/05/06 created.
 */
public interface AsyncServiceFactory {

	/**
	 * @param <T> 型
	 * @param serviceType サービスタイプ
	 * @return サービス
	 */
	public <T> T create(Class<T> serviceType);
}

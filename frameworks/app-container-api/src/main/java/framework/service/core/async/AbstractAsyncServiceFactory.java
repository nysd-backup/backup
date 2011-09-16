/**
 * Use is subject to license terms.
 */
package framework.service.core.async;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import framework.service.core.locator.ServiceLocator;


/**
 * 非同期サービスファクトリ.
 * 非同期処理をProxyとして作成する。
 *
 * @author yoshida-n
 * @version	2011/05/06 created.
 */
public abstract class AbstractAsyncServiceFactory implements AsyncServiceFactory{

	/**
	 * @see framework.service.core.async.AsyncServiceFactory#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<T> serviceType){
		T service = ServiceLocator.lookupByInterface(serviceType);
		//Future<V>を直接返却するAsyncronousサービス以外はプロキシを使用する。原則なしにする。
		if( service != null && serviceType.getAnnotation(getAnnotation()) == null){
			return serviceType.cast(Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, 
				new AsyncServiceProxy()));
		}
		return service;
	}

	/**
	 * @return 非同期処理を示すアノテーション
	 */
	protected abstract Class<? extends Annotation> getAnnotation();
}

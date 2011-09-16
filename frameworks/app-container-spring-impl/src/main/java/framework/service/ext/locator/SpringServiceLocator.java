/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import framework.service.core.locator.ServiceLocator;

/**
 * Spring管理のサービスロケータの基底.
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public abstract class SpringServiceLocator extends ServiceLocator{
	
	/** コンテキスト */
	protected ApplicationContext context = null;	
	
	/** デフォルトサービスマップ （必要があればコンテナ起動時などに初期化する*/
	private static Map<String,String> defaultServiceMap = new ConcurrentHashMap<String,String>();
	
	/**
	 * 初期処理
	 */
	public abstract void construct();
	
	/**
	 * 終了処理
	 */
	public abstract void destroy();
	
	/**
	 * 初期処理
	 */
	protected void initialize(String classpathResource) {
		context = new ClassPathXmlApplicationContext(classpathResource);
		delegate = this;
	}
	
	/**
	 * 終了処理
	 */
	protected void terminate(){		
		if(context != null){
			((ClassPathXmlApplicationContext)context).destroy();
			context = null;
		}
		delegate = null;
	}
	
	/**
	 * サービスを登録する.
	 * コンテナ起動時などの初期処理で実行する
	 * 
	 * @param serviceType サービスタイプ
	 * @param defaultServiceName サービス名
	 */
	protected static void registDefaultService(Class<?> serviceType, String defaultServiceName){
		registDefaultService(StringUtils.uncapitalize(serviceType.getSimpleName()), defaultServiceName);
	}
	
	/**
	 * サービスを登録する.
	 * コンテナ起動時などの初期処理で実行する
	 * 
	 * @param name
	 * @param defaultServiceName
	 */
	protected static void registDefaultService(String name, String defaultServiceName){
		defaultServiceMap.put(name, defaultServiceName);
	}

	/**
	 * @see framework.service.ext.locator.ServiceLocator#lookupDefaultService(java.lang.Class)
	 */
	@Override
	public <T> T lookupDefaultService(Class<T> clazz) {
		String serviceName = StringUtils.uncapitalize(clazz.getSimpleName());
		String name = defaultServiceMap.get(serviceName);
		if(name == null){
			return clazz.cast(context.getBean(serviceName));
		}
		return clazz.cast(context.getBean(name));
	}

	/**
	 * @see framework.service.ext.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookupService(StringUtils.uncapitalize(clazz.getSimpleName())+ "Impl"));
	}

	/**
	 * @see framework.service.ext.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)context.getBean(name);
	}
	
	/**
	 * @see framework.service.ext.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteService(Class<T> serviceType){		
		throw new UnsupportedOperationException();
	}
	
	
}

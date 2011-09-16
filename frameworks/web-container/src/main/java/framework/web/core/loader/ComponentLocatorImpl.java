/**
 * Use is subject to license terms.
 */
package framework.web.core.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * SpringのWebApplicationContextからリソースを取得する
 *
 * @author yoshida-n
 * @version	created.
 */
public class ComponentLocatorImpl extends ComponentLocator{
	
	/** コンテキスト */
	protected WebApplicationContext context = null;	
	
	/** デフォルトサービスマップ （必要があればコンテナ起動時などに初期化する*/
	private static Map<String,String> defaultServiceMap = new ConcurrentHashMap<String,String>();

	/**
	 * @see framework.web.core.loader.ComponentLocator#initialize(org.springframework.web.context.WebApplicationContext)
	 */
	void construct(WebApplicationContext context) {
		this.context = context;
		delegate = this;
	}
	
	/**
	 * @param 終了処理
	 */
	static void terminate(){
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
	 * @see framework.web.core.loader.ComponentLocator#lookupDefaultService(java.lang.Class)
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
	 * @see framework.web.core.loader.ComponentLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookupService(StringUtils.uncapitalize(clazz.getSimpleName())+ "Impl"));
	}


	/**
	 * @see framework.web.core.loader.ComponentLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)context.getBean(name);
	}

}

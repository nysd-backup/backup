/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import framework.service.core.locator.ServiceLocator;
import framework.service.ext.define.ComponentBuilder;

/**
 * サービスロケータ.
 *
 * @author yoshida-n
 * @version	2011/04/07 created.
 */
public class ServiceLocatorImpl extends ServiceLocator{

	/** 通常JNDI名 */
	private static final String PREFIX = "java:module";

	/** デフォルトサービスマップ （必要があればコンテナ起動時などに初期化する*/
	private static Map<String,String> defaultServiceMap = new ConcurrentHashMap<String,String>();
	
	/** コンポーネントビルダ */
	private ComponentBuilder builder;
	
	/**
	 * @param builder コンポーネントビルダ
	 */
	public void initialize(ComponentBuilder componentBuilder){
		builder = componentBuilder;
		delegate = this;
	}
	
	/**
	 * @return　コンポーネントビルダ
	 */
	public static ComponentBuilder getComponentBuilder(){
		return ((ServiceLocatorImpl)delegate).builder;
	}
	
	/**
	 * サービスを登録する.
	 * コンテナ起動時などの初期処理で実行する
	 * 
	 * @param serviceType サービスタイプ
	 * @param defaultServiceName サービス名
	 */
	protected static void registDefaultService(Class<?> serviceType, String defaultServiceName){
		defaultServiceMap.put(serviceType.getName(), defaultServiceName);
	}

	/**
	 * サービス取得
	 * @param <T> サービス
	 * @param serviceType サービスタイプ
	 * @param サービス
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookupByInterface(Class<T> ifType){
		T service =  (T)lookup(ifType.getSimpleName()+"Impl");
		return service;
	}

	/**
	 * @see framework.framework.service.ext.locator.ServiceLocator#lookupDefaultService(java.lang.Class)
	 */
	@Override
	public <T> T lookupDefaultService(Class<T> serviceType) {
		String name = defaultServiceMap.get(serviceType);
		if( name == null){
			if( serviceType.isInterface()){
				return lookupByInterface(serviceType);
			}else{
				return serviceType.cast(lookup(serviceType.getSimpleName()));
			}
		}else {
			return  serviceType.cast(lookup(name));
		}
	}

	/**
	 * @see framework.framework.service.ext.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookup(clazz.getSimpleName() + "Impl",null));
	}

	/**
	 * @see framework.framework.service.ext.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)lookup(name,null);
	}

	/**
	 * @see framework.framework.service.ext.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteService(Class<T> serviceType) {
		Properties properties = new Properties();
	     properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
	     properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
	     properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	     properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	     properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");	//TODO 外だし
	     Object service = lookup(serviceType.getSimpleName() , properties);
	     return serviceType.cast(service);
	}
	
	/**
	 * @param serviceName
	 * @param prop
	 * @return
	 */
	private Object lookup(String serviceName, Properties prop){
		
		try{			
			String format = String.format("%s/%s",PREFIX , serviceName);		
			if(prop == null){				
				return new InitialContext().lookup(format);
			}else{
				return new InitialContext(prop).lookup(format);
			}
		}catch(NamingException ne){
			throw new IllegalArgumentException("Failed to load service ", ne);
		}
	}
}

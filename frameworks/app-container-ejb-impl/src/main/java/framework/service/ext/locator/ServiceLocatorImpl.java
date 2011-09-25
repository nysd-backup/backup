/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;

import java.util.Properties;

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

	/** JNDI名プリフィクス */
	private static final String PREFIX = "java:module";
	
	/** コンポーネントビルダ */
	private ComponentBuilder builder;
	
	/**
	 * @param componentBuilder コンポーネントビルダ
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
	 * サービス取得
	 * @param <T> 型
	 * @param ifType サービスタイプ
	 * @return サービス
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookupByInterface(Class<T> ifType){
		T service =  (T)lookup(ifType.getSimpleName()+"Impl");
		return service;
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookup(clazz.getSimpleName() + "Impl",null));
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)lookup(name,null);
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
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
	 * @param serviceName サービス名
	 * @param prop プロパティ
	 * @return サービス
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

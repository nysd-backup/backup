/**
 * Copyright 2011 the original author
 */
package framework.service.ext.locator;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import framework.service.core.locator.ServiceLocator;
import framework.service.ext.define.ComponentBuilder;

/**
 * A service locator.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends ServiceLocator{

	/** the JNDI prefix */
	private static final String PREFIX = "java:module";
	
	/** the <code>ComponentBuilder</code> */
	private ComponentBuilder builder;
	
	/**
	 * @param componentBuilder the componentBuilder to set
	 */
	public void initialize(ComponentBuilder componentBuilder){
		builder = componentBuilder;
		delegate = this;
	}
	
	/**
	 * @return　the component builder
	 */
	public static ComponentBuilder getComponentBuilder(){
		return ((ServiceLocatorImpl)delegate).builder;
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
	 * @param serviceName the name of service
	 * @param prop the properties to look up
	 * @return the service
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

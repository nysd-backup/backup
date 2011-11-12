/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;


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
	private final ComponentBuilder builder;
	
	/** the remoting properties */
	private final Properties remotingProperties;
	
	/**
	 * @param componentBuilder the componentBuilder to set
	 * @param remotingProperties the remotingProperties to set
	 */
	public ServiceLocatorImpl(ComponentBuilder componentBuilder,Properties remotingProperties){
		this.builder = componentBuilder;
		this.remotingProperties = remotingProperties;
		delegate = this;
	}
	
	/**
	 * @return　the component builder
	 */
	public static ComponentBuilder getComponentBuilder(){
		return ((ServiceLocatorImpl)delegate).builder;
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookup(clazz.getSimpleName() + "Impl",null));
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)lookup(name,null);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteService(Class<T> serviceType) {
	     Object service = lookup(serviceType.getSimpleName() , remotingProperties);
	     return serviceType.cast(service);
	}
	
	/**
	 * @param serviceName the name of service
	 * @param prop the properties to look up
	 * @return the service
	 */
	protected Object lookup(String serviceName, Properties prop){
		
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

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createContext()
	 */
	@Override
	public ServiceContext createContext() {
		return new ServiceContextImpl();
	}
}

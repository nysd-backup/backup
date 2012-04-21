/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.service.core.activation.ServiceLocatorImpl;
import kosmos.framework.service.core.transaction.ServiceContext;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StubServiceLocator extends ServiceLocatorImpl{

	public StubServiceLocator(Properties remotingProperties) {
		super(remotingProperties);	
		delegate = this;
	}
	
	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceTestContextImpl();
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocatorImpl#lookup(java.lang.String, java.util.Properties)
	 */
	@Override
	protected Object lookup(String serviceName, Properties prop){
		
		String test = String.format("java:global/test/test-classes/%s",serviceName);		
		String format = String.format("java:global/test/classes/%s",serviceName);		
		try{
			return lookupFormat(test,prop);
		}catch(Exception e){
			return lookupFormat(format,prop);
		}

	}
	
	/**
	 * @param format
	 * @param prop
	 * @return
	 */
	private Object lookupFormat(String format, Properties prop){
		try{		
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

/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StubServiceLocator extends ServiceLocatorImpl{

	/**
	 * @see framework.service.ext.locator.ServiceLocatorImpl#lookup(java.lang.String, java.util.Properties)
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

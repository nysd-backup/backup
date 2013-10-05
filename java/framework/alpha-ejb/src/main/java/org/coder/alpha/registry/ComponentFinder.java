/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.registry;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * ComponentFindeer 4 EJB.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class ComponentFinder {
	
	/**
	 * Gets the resource
	 * @param name the name 
	 * @param prop the property
	 * @return the resource
	 */
	@SuppressWarnings("unchecked")
	public <T> T getResource(String name, Properties prop){
		return (T)lookup(name,prop);
	}
	
	/**
	 * Gets the resource
	 * @param name the name 
	 * @return the resource
	 */
	@SuppressWarnings("unchecked")
	public <T> T getResource(String name){
		return (T)getResource(name,null);
	}

	/**
	 * Gets the bean
	 * @param name the name 
	 * @param prop the property
	 * @return the resource
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name, Properties prop){
		return (T)lookup(getPrefix() + name,prop);
	}
	
	/**
	 * Gets the bean
	 * @param name the name 
	 * @param prop the property
	 * @return the resource
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name){
		return (T)getBean(name,null);
	}

	/**
	 * Gets the bean
	 * @param requiredType the type
	 * @param prop the prop
	 * @return the bean
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType, Properties prop){
		String name = null;
		if(requiredType.isInterface()){
			name = requiredType.getSimpleName() + "Impl";
		}else {
			name = requiredType.getSimpleName();
		}
		return (T)getBean(name,prop);
	}
	
	/**
	 * Gets the bean
	 * @param requiredType the type
	 * @param prop the prop
	 * @return the bean
	 */
	public <T> T getBean(Class<T> requiredType){
		return getBean(requiredType,null);
	}
	
	/**
	 * @return prefix
	 */
	protected abstract String getPrefix();

	/**
	 * Lookup service by name.
	 * @param serviceName the name of service
	 * @param prop the property
	 * @return bean
	 */
	protected Object lookup(String serviceName, Properties prop){
		InitialContext context = null;
		try{			
			String format = serviceName;	
			if(prop == null){				
				context = new InitialContext(); 				
			}else{
				context = new InitialContext(prop); 		
			}
			return context.lookup(format);
		}catch(NamingException ne){
			throw new IllegalArgumentException("Failed to load alpha.domain ", ne);
		}finally{
			if(context != null){
				try {
					context.close();
				} catch (NamingException e) {					
					e.printStackTrace();
				}
			}
		}
	}

}

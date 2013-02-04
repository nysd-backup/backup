/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.coder.alpha.framework.advice.InternalPerfInterceptor;


/**
 * ComponentFindeer 4 EJB.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class EJBComponentFinder implements ComponentFinder{
	
	/** QueryFactoryFinder */
	private QueryFactoryFinder queryFactoryFinder = new DefaultQueryFactoryFinder();
	
	/** MessageClientFactoryFinder */
	private MessageClientFactoryFinder messageClientFactoryFinder = null; 
	
	/** the trace interceptor */
	private InternalPerfInterceptor internaPerflInterceptor = new InternalPerfInterceptor();
	
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
	 * @see org.coder.alpha.framework.registry.ComponentFinder#getBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name) {
		return (T)getBean(name,null);
	}
	
	/**
	 * @return prefix
	 */
	protected abstract String getPrefix();

	/**
	 * @see org.coder.alpha.framework.registry.ComponentFinder#getBean(java.lang.Class)
	 */
	@Override
	public <T> T getBean(Class<T> requiredType) {
		return (T)getBean(requiredType,null);
	}
	
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
	
	/**
	 * @return the QueryFactoryFinder
	 */
	public QueryFactoryFinder getQueryFactoryFinder() {
		return queryFactoryFinder;
	}

	/**
	 * @param queryFactoryFinder the queryFactoryFinder to set
	 */
	public void setQueryFactoryFinder(QueryFactoryFinder queryFactoryFinder) {
		this.queryFactoryFinder = queryFactoryFinder;
	}

	/**
	 * @return the MessageClientFactoryFinder
	 */
	public MessageClientFactoryFinder getMessageClientFactoryFinder() {
		return messageClientFactoryFinder;
	}

	/**
	 * @param messageClientFactoryFinder the messageClientFactoryFinder to set
	 */
	public void setMessageClientFactoryFinder(
			MessageClientFactoryFinder messageClientFactoryFinder) {
		this.messageClientFactoryFinder = messageClientFactoryFinder;
	}

	/**
	 * @return the InternalPerfInterceptor
	 */
	public InternalPerfInterceptor getInternaPerflInterceptor() {
		return internaPerflInterceptor;
	}

	/**
	 * @param internaPerflInterceptor the internaPerflInterceptor to set
	 */
	public void setInternaPerflInterceptor(InternalPerfInterceptor internaPerflInterceptor) {
		this.internaPerflInterceptor = internaPerflInterceptor;
	}

	
	
}

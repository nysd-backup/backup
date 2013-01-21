/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import alpha.framework.advice.InternalPerfInterceptor;
import alpha.framework.registry.ComponentFinder;
import alpha.framework.transaction.TxVerifier;

/**
 * ComponentFindeer 4 EJB.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class EJBComponentFinder implements ComponentFinder{
	
	/** QueryFactoryFinder */
	private QueryFactoryFinder queryFactoryFinder;
	
	/** MessageClientFactoryFinder */
	private MessageClientFactoryFinder messageClientFactoryFinder; 
	
	/** TxVerifier*/
	private TxVerifier txVerifier;
	
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
		return (T)getBean(requiredType.getSimpleName() + "Impl",prop);
	}

	/**
	 * @see alpha.framework.registry.ComponentFinder#getBean(java.lang.String)
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
	 * @see alpha.framework.registry.ComponentFinder#getBean(java.lang.Class)
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
	 * @return the TxVerifier
	 */
	public TxVerifier getTxVerifier() {
		return txVerifier;
	}

	/**
	 * @param txVerifier the txVerifier to set
	 */
	public void setTxVerifier(TxVerifier txVerifier) {
		this.txVerifier = txVerifier;
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

/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.messaging.client.MessageClientFactoryProvider;
import alpha.framework.domain.query.QueryFactoryProvider;
import alpha.framework.domain.transaction.TxVerifier;

/**
 * AbstractEJBComponentFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractEJBComponentFinder implements EJBComponentFinder{

	/** QueryFactoryProvider */
	private QueryFactoryProvider queryFactoryProvider;
	
	/** MessageClientFactoryProvider */
	private MessageClientFactoryProvider messageClientFactoryProvider; 
	
	/** TxVerifier*/
	private TxVerifier txVerifier;
	
	/** the trace interceptor */
	private InternalPerfInterceptor internaPerflInterceptor = new InternalPerfInterceptor();
	
	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getResource(java.lang.String, java.util.Properties)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getResource(String name, Properties prop){
		return (T)lookup(name,prop);
	}
	
	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getResource(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getResource(String name){
		return (T)getResource(name,null);
	}
	
	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getBean(java.lang.String, java.util.Properties)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name, Properties prop){
		return (T)lookup(getPrefix() + name,prop);
	}

	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getBean(java.lang.Class, java.util.Properties)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType, Properties prop){
		return (T)getBean(requiredType.getSimpleName() + "Impl",prop);
	}

	/**
	 * @see alpha.framework.domain.activation.ComponentFinder#getBean(java.lang.String)
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
	 * @see alpha.framework.domain.activation.ComponentFinder#getBean(java.lang.Class)
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
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getQueryFactoryProvider()
	 */
	@Override
	public QueryFactoryProvider getQueryFactoryProvider() {
		return queryFactoryProvider;
	}

	/**
	 * @param queryFactoryProvider the queryFactoryProvider to set
	 */
	public void setQueryFactoryProvider(QueryFactoryProvider queryFactoryProvider) {
		this.queryFactoryProvider = queryFactoryProvider;
	}

	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getTxVerifier()
	 */
	@Override
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
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getMessageClientFactoryProvider()
	 */
	@Override
	public MessageClientFactoryProvider getMessageClientFactoryProvider() {
		return messageClientFactoryProvider;
	}

	/**
	 * @param messageClientFactoryProvider the messageClientFactoryProvider to set
	 */
	public void setMessageClientFactoryProvider(
			MessageClientFactoryProvider messageClientFactoryProvider) {
		this.messageClientFactoryProvider = messageClientFactoryProvider;
	}

	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getInternaPerflInterceptor()
	 */
	@Override
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

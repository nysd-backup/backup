/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

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

	private QueryFactoryProvider queryFactoryProvider;
	
	private MessageClientFactoryProvider messageClientFactoryProvider; 
	
	private TxVerifier txVerifier;
	
	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getBean(java.lang.String, java.util.Properties)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name, Properties prop){
		return (T)lookup(name,prop);
	}

	/**
	 * @see alpha.framework.domain.activation.EJBComponentFinder#getBean(java.lang.Class, java.util.Properties)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType, Properties prop){
		return (T)lookup(requiredType.getSimpleName() + "Impl",prop);
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
	 * @see alpha.framework.domain.activation.ComponentFinder#getBean(java.lang.Class)
	 */
	@Override
	public <T> T getBean(Class<T> requiredType) {
		return (T)getBean(requiredType,null);
	}
	
	/**
	 * @param serviceName
	 * @param prop
	 * @return
	 */
	protected abstract Object lookup(String serviceName, Properties prop);
	
	/**
	 * @return the queryFactoryProvider
	 */
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
	 * @return the txVerifier
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
	 * @return the messageClientFactoryProvider
	 */
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

	
}

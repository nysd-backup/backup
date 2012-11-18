/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.messaging.client.MessageClientFactoryProvider;
import alpha.framework.domain.query.QueryFactoryProvider;
import alpha.framework.domain.transaction.TxVerifier;

/**
 * ComponentFindeer 4 EJB.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EJBComponentFinder extends ComponentFinder{
	
	/**
	 * Gets the resource by name.
	 * @param name the name
	 * @param prop the property
	 * @return resource
	 */
	<T> T getResource(String name , Properties prop);
	
	/**
	 * Gets the resource by name.
	 * @param name the name
	 * @return resource
	 */
	<T> T getResource(String name);
	
	/**
	 * Gets the bean by name.
	 * @param name the name 
	 * @param prop the property
	 * @return bean
	 */
	<T> T getBean(String name, Properties prop);
		
	/**
	 * Gets the bean by type.
	 * @param requiredType the type
	 * @param prop the property
	 * @return bean
	 */
	<T> T getBean(Class<T> requiredType, Properties prop);
	
	/**
	 * @return the queryFactoryProvider
	 */
	QueryFactoryProvider getQueryFactoryProvider();

	/**
	 * @return the txVerifier
	 */
	TxVerifier getTxVerifier();

	/**
	 * @return the messageClientFactoryProvider
	 */
	MessageClientFactoryProvider getMessageClientFactoryProvider();
	
	/**
	 * @return the internaPerflInterceptor
	 */
	InternalPerfInterceptor getInternaPerflInterceptor();

	
	
}

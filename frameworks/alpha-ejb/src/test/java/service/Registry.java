/**
 * Copyright 2011 the original author
 */
package service;

import org.coder.alpha.framework.messaging.MessageClientFactory;
import org.coder.alpha.framework.registry.ComponentFinder;
import org.coder.alpha.framework.registry.QueryFactoryFinder;

/**
 * Registory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Registry {
	
	
	public static QueryFactoryFinder getQueryFactoryFinder(){
		return new QueryFactoryFinder();
	}
	
	public static ComponentFinder getComponentFinder(){
		return new StubComponentFinder();
	}
	
	public static MessageClientFactory getMessageClientFactoryFinder(){
		return new MessageClientFactory();
	}
}

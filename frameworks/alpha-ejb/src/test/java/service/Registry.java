/**
 * Copyright 2011 the original author
 */
package service;

import org.coder.alpha.framework.registry.ComponentFinder;
import org.coder.alpha.framework.registry.DefaultQueryFactoryFinder;
import org.coder.alpha.framework.registry.MessageClientFactoryFinder;
import org.coder.alpha.framework.registry.ObjectMessageClientFactoryFiinder;
import org.coder.alpha.framework.registry.QueryFactoryFinder;

/**
 * Registory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Registry {
	
	
	public static QueryFactoryFinder getQueryFactoryFinder(){
		return new DefaultQueryFactoryFinder();
	}
	
	public static ComponentFinder getComponentFinder(){
		return new StubComponentFinder();
	}
	
	public static MessageClientFactoryFinder getMessageClientFactoryFinder(){
		return new ObjectMessageClientFactoryFiinder();
	}
}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;


/**
 * UnifiedComponentFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class UnifiedComponentFinder extends ComponentFinder{
	
	/**
	 * @return prefix
	 */
	protected String getPrefix(){
		return "java:module/";
	}
	
}

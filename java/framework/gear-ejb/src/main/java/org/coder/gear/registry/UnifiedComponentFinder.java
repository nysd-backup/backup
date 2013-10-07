/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.registry;


/**
 * UnifiedComponentFinder.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class UnifiedComponentFinder extends ComponentFinder{
	
	/**
	 * @return prefix
	 */
	protected String getPrefix(){
		return "java:module/";
	}
	
}

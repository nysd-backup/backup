/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;


/**
 * UnifiedComponentFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class UnifiedComponentFinder extends EJBComponentFinder{
	
	/**
	 * @return prefix
	 */
	protected String getPrefix(){
		return "java:module/";
	}
	
}

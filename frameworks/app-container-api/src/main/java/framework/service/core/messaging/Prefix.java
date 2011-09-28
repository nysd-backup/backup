/**
 * Copyright 2011 the original author
 */
package framework.service.core.messaging;

/**
 * the prefix of destination.
 * 
 * <pre>
 * 例:jms/{$prefix}/....
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public @interface Prefix {

	/** the prefix */
	String value();
}

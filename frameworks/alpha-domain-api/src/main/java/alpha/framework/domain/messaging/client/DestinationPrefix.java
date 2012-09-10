/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

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
public @interface DestinationPrefix {

	/** the prefix */
	String value();
}

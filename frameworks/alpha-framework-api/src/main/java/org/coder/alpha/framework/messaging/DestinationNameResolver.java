/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.lang.reflect.Method;

/**
 * A selector for JMS's destination
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface DestinationNameResolver {

	/**
	 * @param target the method to invoke
	 * @return　the destination name
	 */
	public String createDestinationName(Method target,MessagingProperty property);
}

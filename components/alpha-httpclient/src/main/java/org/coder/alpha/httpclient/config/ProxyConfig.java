/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Proxy Configuration.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProxyConfig {

	/** host name */
	String host();
	
	/** port number */
	int port();
	
	/** user if needed */
	String user() default "";
	
	/** password if needed */
	String password() default "";
}

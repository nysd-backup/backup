/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public @interface ProxyConfig {

	String host();
	
	int port();
	
	String user() default "";
	
	String password() default "";
}

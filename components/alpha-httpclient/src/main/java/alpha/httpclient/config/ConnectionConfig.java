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
public @interface ConnectionConfig {

	int socketTimeout() default 0;
	
	int connectionTimeout() default 0;
	
	boolean keepAlive() default false;
}

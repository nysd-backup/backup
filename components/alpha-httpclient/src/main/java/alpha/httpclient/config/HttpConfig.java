/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

import org.apache.http.client.params.CookiePolicy;



/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public @interface HttpConfig {
	
	String version() default "1.1";
	
	ProxyConfig proxy();
	
	SchemeConfig[] schema() default {};
	
	ConnectionConfig connection() ;
	
	String cookiePolicy() default CookiePolicy.BROWSER_COMPATIBILITY;

	boolean asynchronous() default false;
}

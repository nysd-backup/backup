/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Connection Configuration.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConnectionConfig {

	/** timeout msec */
	int socketTimeout() default -1;
	
	/** timeout msec */
	int connectionTimeout() default -1;

	/** pool connection */
	boolean poolable() default false;
	
	/** connection is keeping between request or thread */
	KeepAliveScope scope() default KeepAliveScope.REQUEST;
}

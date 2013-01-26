/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Scheme.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemeConfig {

	String name();
	
	int port();
}

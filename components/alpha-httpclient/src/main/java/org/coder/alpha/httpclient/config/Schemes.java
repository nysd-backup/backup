/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Schemes.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Schemes {

	SchemeConfig[] schemes() default {};
}

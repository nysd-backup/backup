/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.txscriptejb.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Traceable.
 *
 * @author yoshida-n
 * @version	1.0
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Traceable {

}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * JMSConfig.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Consumes {

	/** JMSタイプ .*/
	String type() default "";
	
	/** 宛先JNDI .*/
	String destination() default "";
}

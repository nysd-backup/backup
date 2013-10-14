/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.jms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * JMSConfig.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Consumes {

	/** JMSタイプ .*/
	String type() default "";
	
	/** 宛先JNDI .*/
	String destination() default "";
}

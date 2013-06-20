/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

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
public @interface Produceable {

	/** JMSタイプ .*/
	String type() default "";
	
	/** 優先度 .*/
	int priority() default -1;
	
	/** 宛先JNDI .*/
	String destinationName() default "";
}

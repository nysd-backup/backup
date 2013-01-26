/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging.client;

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
public @interface JMSConfig {

	String jmsType() default "";

	JMSProperty[] property() default {};
	
	String destinationPrefix() default "";
	
	String destinationName() default "";
}

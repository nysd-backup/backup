/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set this annotation to <code>FreeQuery</code>.
 * 
 * <code>javax.persistence.NamedQuery</code> and <code>javax.persistence.NamedNativeQuery</code> 
 * can not be used if this annotation is set.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousQuery {

	/** the path or statement of query */
    String query();

    /** the type of the result (Java bean or Map is available). */
    @SuppressWarnings("rawtypes")
	Class resultClass() default void.class; 
    
}

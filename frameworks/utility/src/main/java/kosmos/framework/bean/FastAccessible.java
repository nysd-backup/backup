/**
 * Copyright 2011 the original author
 */
package kosmos.framework.bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FastAccessible {
	Class<? extends PropertyAccessor<?>> propertyAccessor();
}

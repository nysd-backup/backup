/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/05/13 created.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceCallable {

}

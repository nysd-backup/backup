/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * サービスファサードを示すアノテーション.
 *
 * @author yoshida-n
 * @version	2011/05/11 created.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceFacade {

	/** エイリアス */
	String alias() default "";
	
}

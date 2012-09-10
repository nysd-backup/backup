/**
 * Copyright 2011 the original author
 */
package alpha.framework.web.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ActionMethodeであることを示す.
 * 
 * <pre>
 * 	このアノテーションが設定されているメソッドにActionInterceptorを設定する。
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionMethod {

}

/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for result type.
 * this indicates that getters and setters are declared. 
 *
 * @author yoshida-n
 * @version	created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessorDeclared {

}

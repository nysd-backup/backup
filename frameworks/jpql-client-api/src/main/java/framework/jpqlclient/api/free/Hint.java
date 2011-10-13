/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.persistence.QueryHint;


/**
 * The JPA hint.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Hint {

	/** JPA hint */
	QueryHint[] hitns();
}

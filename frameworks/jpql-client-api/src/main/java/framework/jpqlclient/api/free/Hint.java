/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import javax.persistence.QueryHint;


/**
 * The JPA hint.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public @interface Hint {

	/** JPA hint */
	QueryHint[] hitns();
}

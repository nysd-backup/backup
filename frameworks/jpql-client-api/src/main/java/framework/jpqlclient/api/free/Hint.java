/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import javax.persistence.QueryHint;


/**
 * JPA用ヒント.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public @interface Hint {

	/** JPA用ヒント. */
	QueryHint[] hitns();
}

/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.free;

import javax.persistence.QueryHint;


/**
 * JPA用ヒント.
 *
 * @author yoshida-n
 * @version	created.
 */
public @interface Hint {

	/** JPA用ヒント. */
	QueryHint[] hitns();
}

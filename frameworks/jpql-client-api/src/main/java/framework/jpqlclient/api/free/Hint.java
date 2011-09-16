/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.free;

import javax.persistence.QueryHint;


/**
 * JPAベンダーヒント.
 *
 * @author yoshida-n
 * @version	created.
 */
public @interface Hint {

	/** ベンダーヒント. */
	QueryHint[] hitns();
}

/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api;

/**
 * JPA用ヒント(ベンダ非依存）.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JPAQueryHints {

	/** ロックタイムアウト */
	public static final String PESSIMISTIC_READ_TIMEOUT = "javax.persistence.lock.timeout";
}

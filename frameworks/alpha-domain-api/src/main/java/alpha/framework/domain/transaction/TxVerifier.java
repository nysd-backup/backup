/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;


/**
 * Tests if the current transaction must be rolled back.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface TxVerifier {

	/**
	 * Tests if the current transaction must be rolled back.
	 * 
	 * @param value object
	 * @return true:should rollback
	 */
	public boolean isRollbackRequired(Object value);
}

/**
 * Use is subject to license terms.
 */
package framework.service.ext.transaction;

import framework.service.core.transaction.InternalUnitOfWork;

/**
 * トランザクション識別可能な作業単位.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NamedInternalUnitOfWork extends InternalUnitOfWork {

	/** トランザクションID */
	private String transactionId = null;
	
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	
}

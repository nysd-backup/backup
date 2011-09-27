/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import framework.service.core.transaction.InternalUnitOfWork;

/**
 * トランザクション識別可能な作業単位.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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

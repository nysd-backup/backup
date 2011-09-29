/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import framework.service.core.transaction.InternalUnitOfWork;

/**
 * The named unit of work.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NamedInternalUnitOfWork extends InternalUnitOfWork {

	/** the transaction id */
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

/**
 * Copyright 2011 the original author
 */
package service.test;

import org.springframework.stereotype.Service;

import alpha.framework.transaction.TxVerifier;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Service
public class TransactionVerifierImpl implements TxVerifier{

	@Override
	public boolean isRollbackRequired(Object value) {
		return value.toString().equals("100");
	}

}

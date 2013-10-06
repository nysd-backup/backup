/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.exception;

import javax.persistence.PersistenceException;

/**
 * DeadLockException.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class DeadLockException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param e the cause
	 */
	public DeadLockException(Exception e){
		super(e);
	}
}

/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.exception;

import javax.persistence.PersistenceException;

/**
 * UniqueConstraintException.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class UniqueConstraintException extends PersistenceException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param e the cause
	 */
	public UniqueConstraintException(Exception e){
		super(e);
	}
}

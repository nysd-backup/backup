/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.exception;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

/**
 * UniqueConstraintException.
 *
 * @author yoshida-n
 * @version	created.
 */
public class UniqueConstraintException extends PersistenceException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param e the cause
	 */
	public UniqueConstraintException(SQLException e){
		super(e);
	}
}

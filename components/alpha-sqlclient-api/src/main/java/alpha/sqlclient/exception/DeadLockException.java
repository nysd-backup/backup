/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.exception;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

/**
 * DeadLockException.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DeadLockException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param e the cause
	 */
	public DeadLockException(SQLException e){
		super(e);
	}
}

/**
 * Copyright 2011 the original author
 */
package service;

import javax.ejb.ApplicationException;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@ApplicationException(rollback=true)
public class AppException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

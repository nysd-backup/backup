/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.ApplicationException;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@ApplicationException(rollback=true)
public class BusinessException extends RuntimeException{

	public BusinessException(String string) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

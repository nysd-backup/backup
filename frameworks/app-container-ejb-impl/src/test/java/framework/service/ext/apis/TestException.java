package framework.service.ext.apis;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class TestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}

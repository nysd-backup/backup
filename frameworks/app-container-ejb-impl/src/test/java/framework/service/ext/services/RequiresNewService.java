/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RequiresNewService {

	public String test();
	
	public void persist();
	
	public void addMessage();
	
	public String crushException();
	
	public void callTwoServices();
	
	public int getState();
	
	public boolean isRollbackOnly();
	
	public void throwError();
}

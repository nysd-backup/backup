/**
 * Copyright 2011 the original author
 */
package service.test;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RequiresNewNativeService {

	public String test();
	
	public String another();
	
	public void addMessage();
	
	public String crushException();
	
	public void callTwoServices();
	
	public int getState();
	
	public boolean isRollbackOnly();
	
	public void throwError();
}

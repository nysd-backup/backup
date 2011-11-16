/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.test.services;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface LocalService {

	public Object normal(Object request);
	
	public Object throwError(Object request);
	
	public Object addMessage(Object request);
	
	public Object databaseError(Object request);
}

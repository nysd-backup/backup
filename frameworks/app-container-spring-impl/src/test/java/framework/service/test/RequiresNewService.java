/**
 * Use is subject to license terms.
 */
package framework.service.test;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RequiresNewService {

	public String test();
	
	public void addMessage();
	
	public String crushException();
	
	public void callTwoServices();
	
	public int getState();
	
	public boolean isRollbackOnly();
	
	public void throwError();
}

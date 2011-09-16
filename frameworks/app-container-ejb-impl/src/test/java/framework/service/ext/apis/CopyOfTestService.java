/**
 * Use is subject to license terms.
 */
package framework.service.ext.apis;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/03/31 created.
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
public interface CopyOfTestService {

	public void test() throws TestException;
}

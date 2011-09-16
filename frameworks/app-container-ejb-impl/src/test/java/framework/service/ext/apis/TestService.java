/**
 * Use is subject to license terms.
 */
package framework.service.ext.apis;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/03/31 created.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public interface TestService {

	public void test();
}

/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import java.sql.Connection;
import framework.service.core.transaction.InternalUnitOfWork;
import framework.service.core.transaction.ServiceContext;
import framework.service.core.transaction.TransactionManagingContext;
import framework.sqlclient.api.ConnectionProvider;

/**
 * SQLエンジン用のコネクション取得[データソース経由].
 * 
 * <pre>
 * JTA専用。JTA以外で使用するとgetConnection毎にコネクションが変化してしまう。
 * Springの場合にDataSourceUtilsを使用しても解決不可能。
 * 
 * WEB層からの直接検索で使用する場合には、一トランザクションに一回のSELECTなのでJTAでなくても使用可能。
 * </pre>
 * 
 * @author yoshida-n
 * @version	created.
 */
public class DataSourceConnectionProviderImpl implements ConnectionProvider{

	/**
	 * @see framework.sqlclient.api.free.ConnectionProvider#getConnection(javax.persistence.EntityManager)
	 */
	@Override
	public Connection getConnection() {	
		TransactionManagingContext context = (TransactionManagingContext)ServiceContext.getCurrentInstance();
		InternalUnitOfWork current = context.getCurrentUnitOfWork();
		return current.getCurrentConnection();		
	}

}

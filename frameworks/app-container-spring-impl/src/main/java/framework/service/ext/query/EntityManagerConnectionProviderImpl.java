/**
 * Use is subject to license terms.
 */
package framework.service.ext.query;

import java.sql.Connection;

import javax.persistence.EntityManager;

import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.server.ClientSession;

import framework.jpqlclient.api.EntityManagerProvider;
import framework.sqlclient.api.ConnectionProvider;

/**
 * SQLエンジン用のコネクション取得[エンティティマネージャ経由].
 * 
 * <pre>
 * 前提として、JTA以外のトランザクションマネージャで使用すること。
 * JTAの時はDataSourceから取得してもコネクション不整合を起こさないため使用する必要はない。
 * 
 * <p>
 * DataSourceからの直接取得やDataSourceUtils#getConnection(DataSource)では、
 * 現在実行中のトランザクションが使用しているコネクションとは異なるコネクションが使用されてしまうためデータ不整合となるため
 * 帳票出力などビジネスロジックでSQLエンジンを利用する場合はこのクラスを使用してコネクションを取得すること。
 * 検索しか実行しないのであればDataSource経由でも問題ない。
 *
 * このクラスを使用する場合トランザクションを開始していないとgetConnection()時にNPEが発生するため、必ずトランザクションを開始して実行すること。
 * TransactionalアノテーションをつけていてもreadOnly=trueとなっていたらNG。
 * </p>
 * 
 * </pre>
 * @author yoshida-n
 * @version	2011/05/03 created.
 */
public class EntityManagerConnectionProviderImpl implements ConnectionProvider{

	/** エンティティマネージャ */
	private EntityManager em;
	
	/**
	 * @param provider プロバイダ
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	
	
	/**
	 * @see framework.sqlclient.api.free.ConnectionProvider#getConnection(javax.persistence.EntityManager)
	 */
	@Override
	public Connection getConnection() {	
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();		
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		return accessor.getConnection();
	}

}

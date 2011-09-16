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
 * WEB層からの直接検索はJTAでなくてもDataSource経由でも問題ないため、帳票出力などビジネスロジック内で使用する場合専用。
 * トランザクションを開始していないとgetConnection()時にNPEが発生するため、必ずトランザクションを開始して実行すること。
 * TransactionalアノテーションをつけていてもreadOnly=trueとなっていたらNG。
 * また、帳票出力処理など、ビジネスロジック内でのSQLエンジン用のコネクションは必ずこれを使用すること。
 * DataSourceからの直接取得やDataSourceUtils#getConnection(DataSource)では、
 * 現在実行中のトランザクションが使用しているコネクションとは異なるコネクションが使用されてしまうためデータ不整合となる。
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

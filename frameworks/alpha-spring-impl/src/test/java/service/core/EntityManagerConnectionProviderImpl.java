/**
 * Copyright 2011 the original author
 */
package service.core;

import java.sql.Connection;

import javax.persistence.EntityManager;



import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.server.ClientSession;

import client.sql.ConnectionProvider;
import client.sql.elink.EntityManagerProvider;



/**
 * SQLエンジン用のコネクション取得[エンティティマネージャ経由].
 * <b>Spring専用</b>
 * 
 * <pre>
 * 前提として、JTA以外のトランザクションマネージャで使用すること。
 * JTAの時にはこのクラスを使用する必要はなく、DataSourceからの取得でよい。
 * 本番ではJTAを使用することがほとんどのはずなので、基本的にJTAが使用できないローカルでの単体テストで使用する。
 * 
 * JTA以外の場合、DataSourceからの直接取得やDataSourceUtils#getConnection(DataSource)では、
 * 現在実行中のトランザクションが使用しているコネクションとは異なるコネクションが使用されてしまいデータ不整合となるため、
 * 帳票出力などビジネスロジック層でSQLエンジンを利用する場合にはこのクラスを使用してコネクションを取得すること。
 * 検索しか実行しないのであればDataSource経由でも問題ない。
 *
 * このクラスを使用する場合トランザクションを開始していなければgetConnection()時にNPEが発生するため、必ずトランザクションを開始して実行すること、
 * TransactionalアノテーションをつけていてもreadOnly=trueとなっていたらNG
 * </p>
 * 
 * </pre>
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class EntityManagerConnectionProviderImpl implements ConnectionProvider{

	/** エンティティマネージャ */
	private EntityManager em;
	
	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	

	/**
	 * @see client.sql.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();		
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		return accessor.getConnection();
	}

}

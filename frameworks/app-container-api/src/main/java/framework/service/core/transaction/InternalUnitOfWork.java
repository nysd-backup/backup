/**
 * Use is subject to license terms.
 */
package framework.service.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.query.DataSourceManager;

/**
 * 作業単位.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalUnitOfWork {
	
	/** ロールバックフラグ. 一度設定すると解除不可能 */
	private boolean rollbackOnly = false;
	
	/** SQLエンジン専用のコネクション（帳票/WEB用） */
	private Connection currentConnection = null;

	/**
	 * ロールバックフラグをonにする
	 */
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @return the rollbackOnly
	 */
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}
	
	/**
	 * @return 現在トランザクションで利用可能なコネクション
	 */
	public Connection getCurrentConnection(){
		if(currentConnection == null){
			try {
				currentConnection = ServiceLocator.lookupByInterface(DataSourceManager.class).getDataSource().getConnection();
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
		return currentConnection;
	}

	/**
	 * 終了
	 */
	public void terminate(){
		if(currentConnection != null){
			try {
				currentConnection.close();
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}
	

}

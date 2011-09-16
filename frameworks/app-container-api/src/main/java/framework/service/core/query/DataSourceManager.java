/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import javax.sql.DataSource;

/**
 * データソース取得.
 * <pre>
 * JTA用コネクションプールから取得すること。
 * Oracleの場合はoracle.jdbc.xa.client.OracleXADataSource。
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public interface DataSourceManager {

	/**
	 * @return データソース
	 */
	public DataSource getDataSource();
}

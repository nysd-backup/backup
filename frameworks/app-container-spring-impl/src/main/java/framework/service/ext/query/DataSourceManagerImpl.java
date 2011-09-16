/**
 * Use is subject to license terms.
 */
package framework.service.ext.query;

import javax.sql.DataSource;

import framework.service.core.query.DataSourceManager;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DataSourceManagerImpl implements DataSourceManager{

	/** データソース */
	private DataSource dataSource;
	
	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * @see framework.service.core.query.DataSourceManager#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

}

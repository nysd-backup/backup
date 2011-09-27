/**
 * Copyright 2011 the original author
 */
package framework.service.ext.query;

import javax.sql.DataSource;

import framework.service.core.query.DataSourceManager;

/**
 * DIされたデータソースの取得.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DataSourceManagerImpl implements DataSourceManager{

	/** データソース */
	private DataSource dataSource;
	
	/**
	 * @param dataSource　the dataSource to set
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

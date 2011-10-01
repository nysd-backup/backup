/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import framework.sqlengine.executer.RecordFilter;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.RecordHandlerFactory;
import framework.sqlengine.executer.ResultSetHandler;
import framework.sqlengine.facade.QueryResult;

/**
 * Handles the <code>ResultSet</code> to get the data.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ResultSetHandlerImpl implements ResultSetHandler{
	
	/** the factory */
	private RecordHandlerFactory factory = new RecordHandlerFactoryImpl();
	
	/**
	 * @param factory the factory to set
	 */
	public void setFactory(RecordHandlerFactory factory){
		this.factory = factory;
	}
	
	/**
	 * @see framework.sqlengine.executer.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, int, boolean, java.lang.String, framework.sqlengine.executer.RecordFilter)
	 */
	@Override
	public <T> QueryResult<T> getResultList(ResultSet resultSet, Class<T> resultType,int maxSize, 
			boolean totalEnabled, String sqlId,RecordFilter<T> filter) throws SQLException{ 

		int hitCount = 0;
		List<T> result = new ArrayList<T>();			
		boolean limitted = false;
		RecordHandler<T> handler = factory.create(resultType, resultSet);
		while (resultSet.next()) {
			hitCount++;			
			//最大件数超過していたら終了
			if( hitCount > maxSize && maxSize > 0){
				limitted = true;				
				if(!totalEnabled){
					break;
				}
			}
			if(!limitted){
				// 1行の情報カラム取得
				T row = handler.getRecord(resultSet);		
			
				//必要に応じて加工
				if( filter != null){
					filter.edit(row);
				}
				result.add(row);
			}
		}

		return new QueryResult<T>(limitted, result, hitCount);
	}
	
}

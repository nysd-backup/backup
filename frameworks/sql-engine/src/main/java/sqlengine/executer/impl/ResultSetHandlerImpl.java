/**
 * Copyright 2011 the original author
 */
package sqlengine.executer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sqlengine.executer.RecordFilter;
import sqlengine.executer.RecordHandler;
import sqlengine.executer.RecordHandlerFactory;
import sqlengine.executer.ResultSetHandler;
import sqlengine.facade.QueryResult;



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
	 * @see sqlengine.executer.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, sqlengine.executer.RecordFilter, int, int)
	 */
	@Override
	public QueryResult getResultList(ResultSet resultSet, Class<?> resultType,RecordFilter filter, int maxSize)
	throws SQLException{ 

		int hitCount = 0;
		List<Object> result = new ArrayList<Object>();			
		boolean limitted = false;
		RecordHandler handler = factory.create(resultType, resultSet);
		int startPosition = resultSet.getRow();		
		while (resultSet.next()) {
			hitCount++;			
			//最大件数超過していたら終了
			if( !limitted ){
				if(hitCount > maxSize && maxSize > 0){
					limitted = true;	
					if(resultSet.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE){
						resultSet.last();
						hitCount = resultSet.getRow() - startPosition;						
						break;
					}else{
						continue;
					}
				}
				// 1行の情報カラム取得
				Object row = handler.getRecord(resultSet);		
			
				//必要に応じて加工
				if( filter != null){
					filter.edit(row);
				}
				result.add(row);
			}			
		}

		return new QueryResult(limitted, result, hitCount);
	}

	/**
	 * @see sqlengine.executer.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, sqlengine.executer.RecordFilter)
	 */
	@Override
	public <T> List<T> getResultList(ResultSet rs, Class<?> resultType,RecordFilter filter) throws SQLException {
		
		List<T> result = new ArrayList<T>();				
		RecordHandler handler = factory.create(resultType, rs);
		while (rs.next()) {			
			// 1行の情報カラム取得
			@SuppressWarnings("unchecked")
			T row = (T)handler.getRecord(rs);					
			//必要に応じて加工
			if( filter != null){
				filter.edit(row);
			}
			result.add(row);
		}
		return result;
	}
	
}

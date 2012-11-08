/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alpha.jdbc.domain.TotalData;
import alpha.jdbc.strategy.RecordFilter;
import alpha.jdbc.strategy.RecordHandler;
import alpha.jdbc.strategy.RecordHandlerFactory;
import alpha.jdbc.strategy.ResultSetHandler;




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
	 * @see alpha.jdbc.strategy.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, alpha.jdbc.strategy.RecordFilter, int, int)
	 */
	@Override
	public TotalData getResultList(ResultSet resultSet, Class<?> resultType,RecordFilter filter, int maxSize)
	throws SQLException{ 

		List<Object> result = new ArrayList<Object>();			
		boolean limitted = false;
		RecordHandler handler = factory.create(resultType, resultSet);
		int hitCount = 0;
		int startPosition = resultSet.getRow();
		while (resultSet.next()) {	
			hitCount++;
			//最大件数超過していたら終了
			if( !limitted ){
				if( maxSize > 0 && hitCount > maxSize){
					limitted = true;	
					if(resultSet.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE){
						resultSet.last();	
						hitCount = resultSet.getRow();
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
		hitCount = limitted ? hitCount:hitCount+startPosition;
		return new TotalData(limitted, result, hitCount);
	}

	/**
	 * @see alpha.jdbc.strategy.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, alpha.jdbc.strategy.RecordFilter)
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

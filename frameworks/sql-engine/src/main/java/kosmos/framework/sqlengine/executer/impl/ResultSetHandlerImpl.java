/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kosmos.framework.sqlengine.executer.RecordFilter;
import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.facade.QueryResult;


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
	 * @see kosmos.framework.sqlengine.executer.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, kosmos.framework.sqlengine.executer.RecordFilter, int, int)
	 */
	@Override
	public QueryResult getResultList(ResultSet resultSet, Class<?> resultType,RecordFilter filter, int maxSize,int firstResult)
	throws SQLException{ 

		int hitCount = 0;
		List<Object> result = new ArrayList<Object>();			
		boolean limitted = false;
		RecordHandler handler = factory.create(resultType, resultSet);
		while (resultSet.next()) {
			hitCount++;			
			//最大件数超過していたら終了
			if( !limitted ){
				if(hitCount > maxSize && maxSize > 0){
					limitted = true;	
					if(resultSet.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE){
						resultSet.last();
						hitCount = resultSet.getRow() - firstResult;						
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
	 * @see kosmos.framework.sqlengine.executer.ResultSetHandler#getResultList(java.sql.ResultSet, java.lang.Class, kosmos.framework.sqlengine.executer.RecordFilter)
	 */
	@Override
	public <T> List<T> getResultList(ResultSet rs, Class<?> resultType,RecordFilter filter) throws SQLException {
		
		List<T> result = new ArrayList<T>();				
		RecordHandler handler = factory.create(resultType, rs);
		while (rs.next()) {			
			// 1行の情報カラム取得
			T row = handler.getRecord(rs);					
			//必要に応じて加工
			if( filter != null){
				filter.edit(row);
			}
			result.add(row);
		}
		return result;
	}

	/**
	 * @see kosmos.framework.sqlengine.executer.ResultSetHandler#skip(java.sql.ResultSet, int)
	 */
	@Override
	public void skip(ResultSet rs, int firstResult) throws SQLException {
		if(firstResult > 0 ){	
			if(rs.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE ){
				rs.absolute(firstResult);
			}else{
				for(int i=0; i < firstResult;i++){
					rs.next();
				}
			}				
		}
	}
	
}

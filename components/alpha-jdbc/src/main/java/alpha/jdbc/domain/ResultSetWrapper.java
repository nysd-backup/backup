/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import alpha.jdbc.exception.ExceptionHandler;
import alpha.jdbc.exception.QueryException;
import alpha.jdbc.strategy.RecordFilter;
import alpha.jdbc.strategy.RecordHandler;
import alpha.jdbc.strategy.RecordHandlerFactory;
import alpha.jdbc.strategy.ResultSetHandler;


/**
 * Wrapper of the ResultSet.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ResultSetWrapper {
	
	/** the resultSet */
	private final ResultSet resultSet;
	
	/** the filter */
	private RecordFilter filter;
	
	/**
	 * @param filter the filter
	 */
	public void setFilter(RecordFilter filter ){
		this.filter = filter;
	}
	
	/**
	 * @param resultSet the resultSet
	 */
	public ResultSetWrapper(ResultSet resultSet){
		this.resultSet = resultSet;
	}
	
	/**
	 * @param startPosition the startPosition
	 * @throws SQLException
	 */
	public void setStartPosition(int startPosition) throws SQLException{
		if(startPosition > 0){
			if(resultSet.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE ){
				resultSet.absolute(startPosition);
			}else{
				for(int i=0; i < startPosition;i++){
					resultSet.next();
				}
			}		
		}
	}
	
	/**
	 * Gets the total result into the memory
	 * @param type the type
	 * @param handler the handler
	 * @return
	 */
	public <T> List<T> loadIntoMemory(Class<T> type,ResultSetHandler handler)throws SQLException{
		return handler.getResultList(resultSet, type, filter);
	}
	
	/**
	 * Gets the total result into the memory.
	 * @param type the type 
	 * @param maxSize the max sie
	 * @param handler the handler
	 * @return the total result
	 * @throws SQLException
	 */
	public <T> TotalData loadIntoMemory(Class<T> type,ResultSetHandler handler,int maxSize)
	throws SQLException {
		return handler.getResultList(resultSet, type, filter, maxSize);
	}
	
	/**
	 * Gets the first record of the first column.
	 * @param type the type
	 * @return the result
	 * @throws SQLException
	 */
	public <T> T getOnlyOne(Class<T> type) throws SQLException{
		resultSet.next();
		return type.cast(resultSet.getObject(1));		
	}
	
	/**
	 * Gets the cursor.
	 * @param exceptionHandler the exception handler
	 * @param recordHandlerFactory the factory
	 * @param resultType the result type
	 * @return the LazyList
	 * @throws SQLException
	 */
	public <T> LazyList<T> getLazyList(ExceptionHandler exceptionHandler, RecordHandlerFactory recordHandlerFactory,Class<T> resultType) throws SQLException {
		RecordHandler recordHandler = recordHandlerFactory.create(resultType, resultSet);
		return new LazyList<T>(resultSet,recordHandler,exceptionHandler,filter);
	}
	
	/**
	 * Close the cursor
	 */
	public void close(){
		try{
			resultSet.close();
		}catch(SQLException sqle){
			throw new QueryException(sqle);
		}
	}
	
}

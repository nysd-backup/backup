/**
 * 
 */
package org.coder.gear.query.free.result;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.coder.gear.query.free.mapper.MetadataMapper;
import org.coder.gear.query.free.mapper.MetadataMapperFactory;
import org.eclipse.persistence.queries.ScrollableCursor;

/**
 * @author yoshida-n
 *
 */
public class CursorAdapter<T> implements CloseableIterator<T>{

	private ScrollableCursor cursor;
	
	private ResultSet rs;
	
	private MetadataMapper mapper;
	
	/**
	 * @param cursor
	 */
	public CursorAdapter(ScrollableCursor cursor){
		this.cursor = cursor;
		this.rs = cursor.getResultSet();		
	}
	
	/**
	 * @param resultType
	 * @param factory
	 */
	public void construct(Class<?> resultType ,MetadataMapperFactory factory) {
		try {
			mapper = factory.create(resultType, rs);
		} catch (Exception e) {
			close();
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		try {
			return rs.next();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
		try{
			return mapper.getRecord(rs);
		}catch(SQLException e){
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() {
		if(!cursor.isClosed()){
			cursor.close();
		}
	}
	
	/**
	 * 
	 */
	public void moveLast(){
		try{
			rs.last();
		}catch(SQLException e){
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * @return
	 */
	public int getCurrentRow() {
		try{
			return rs.getRow();
		}catch(SQLException e){
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * @return
	 */
	public boolean isTypeScrollableInsensitive(){
		try{
			return rs.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE;
		}catch(SQLException e){
			throw new PersistenceException(e);
		}
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

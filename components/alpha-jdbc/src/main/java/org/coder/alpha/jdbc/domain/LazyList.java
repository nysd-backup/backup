/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.coder.alpha.jdbc.exception.ExceptionHandler;
import org.coder.alpha.jdbc.strategy.RecordFilter;
import org.coder.alpha.jdbc.strategy.RecordHandler;





/**
 * The list holding the <code>ResultSet</code> so as to get next record only when the get(i) is called.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LazyList<E> implements List<E>{

	/** the iterator to fetch */
	private final Cursor itr;

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize(){
		itr.close();
	}
	
	/**
	 * @param rs the rs
	 * @param handler the handler
	 * @param exceptionHandler the exceptionHandler
	 */
	public LazyList(ResultSet rs, RecordHandler handler,ExceptionHandler exceptionHandler,RecordFilter filter){
		this.itr = new Cursor(rs, handler,exceptionHandler,filter);
	}
	
	/**
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		throw new UnsupportedOperationException();	
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();				
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return itr;
	}

	/**
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		itr.close();
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#get(int)
	 */
	@Override
	public E get(int index) {
		throw new UnsupportedOperationException();
	}
	
	private class Cursor implements Iterator<E>{

		/** the result set */
		private final ResultSet rs;
		
		/** the record handler */
		private final RecordHandler handler;
		
		private final ExceptionHandler exceptionHandler;
		
		private final RecordFilter filter;
		
		public Cursor(ResultSet rs ,RecordHandler handler,ExceptionHandler exceptionHandler,RecordFilter filter){
			this.rs = rs;
			this.handler = handler;
			this.exceptionHandler =exceptionHandler ;
			this.filter = filter;
		}
		
		/**
		 * Closes the statement and result set
		 */
		public void close(){
	
			if(rs != null){
				Statement stmt = null;
				try{
					stmt = rs.getStatement();
				} catch (SQLException e) {
				}
				try{
					if(!rs.isClosed()){
						rs.close();
					}
				} catch (SQLException e) {
				}finally{
					try{
						if(stmt != null && !stmt.isClosed()){
							stmt.close();
						}
					}catch(SQLException sqle){
					}					
				}
			}
			
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {			
			
			try{
				boolean hasNext = rs.next();
				if(!hasNext){
					close();
				}
				return hasNext;
			}catch(SQLException t){
				close();
				throw exceptionHandler.rethrow(t);
			}catch(RuntimeException re){
				close();
				throw re;
			}catch(Error re){
				close();
				throw re;
			}
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			try{
				E record = (E)handler.getRecord(rs);
				return filter != null ? filter.edit(record):record;
			}catch(SQLException t){
				close();
				throw exceptionHandler.rethrow(t);
			}catch(RuntimeException re){
				close();
				throw re;
			}catch(Error re){
				close();
				throw re;
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
}

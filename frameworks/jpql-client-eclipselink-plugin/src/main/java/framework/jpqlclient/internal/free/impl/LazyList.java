/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.persistence.queries.ScrollableCursor;

import framework.sqlengine.executer.RecordHandler;

/**
 * The list holiding the <code>ResultSet</code> so as to get next record only when the get(i) is called.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LazyList<E> implements List<E>{

	private static final long serialVersionUID = 1L;

	/** the iterator to fetch */
	private final ResultSetIterator itr;

	/**
	 * @param statement the statement
	 * @param rs the rs
	 * @param maxSize the maxSize
	 * @param handler the handler
	 */
	public LazyList(ScrollableCursor cursor ,RecordHandler<E> handler){
		this.itr = new ResultSetIterator(cursor,handler);
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
		throw new UnsupportedOperationException();
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
	
	
	private class ResultSetIterator implements Iterator<E>{
		
		/** the cursor */
		private final ScrollableCursor cursor;
 		
		private final RecordHandler<E> handler;

		private final ResultSet rs;
		
		public ResultSetIterator(ScrollableCursor cursor,RecordHandler<E> handler){
			this.cursor = cursor;
			this.handler = handler;
			this.rs = cursor.getResultSet();
		}

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			try{
				boolean result = rs.next();
				if(!result){					
					close();					
				}
				return result;
			} catch (SQLException e) {
				close();
				throw new IllegalStateException(e);
			}	
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			return handler.getRecord(rs);
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();			
		}
		
		public void close(){
			cursor.close();
		}
		
	}
}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jdoclient.internal;

import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;

/**
 * The iterator that can close automatically.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ClosableExtent<T> implements Extent<T>{
	
	/** the delegate */
	private final Extent<T> delegate;

	/**
	 * @param delegate　the delegate to set
	 */
	public ClosableExtent(Extent<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @see javax.jdo.Extent#close(java.util.Iterator)
	 */
	@Override
	public void close(Iterator<T> arg0) {
		delegate.close(arg0);		
	}

	/**
	 * @see javax.jdo.Extent#closeAll()
	 */
	@Override
	public void closeAll() {
		delegate.closeAll();
	}

	/**
	 * @see javax.jdo.Extent#getCandidateClass()
	 */
	@Override
	public Class<T> getCandidateClass() {
		return delegate.getCandidateClass();
	}

	/**
	 * @see javax.jdo.Extent#getFetchPlan()
	 */
	@Override
	public FetchPlan getFetchPlan() {
		return delegate.getFetchPlan();
	}

	/**
	 * @see javax.jdo.Extent#getPersistenceManager()
	 */
	@Override
	public PersistenceManager getPersistenceManager() {
		return delegate.getPersistenceManager();
	}

	/**
	 * @see javax.jdo.Extent#hasSubclasses()
	 */
	@Override
	public boolean hasSubclasses() {
		return delegate.hasSubclasses();
	}

	/**
	 * @see javax.jdo.Extent#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return new ClosableIterator<T>(delegate.iterator(), delegate);
	}
	
	
	
	private static class ClosableIterator<T> implements Iterator<T>{
		
		private final Extent<T> extent;
		
		private final Iterator<T> delegate;

		/**
		 * @param delegate
		 * @param source
		 */
		public ClosableIterator(Iterator<T> delegate, Extent<T> source){
			this.delegate = delegate;
			this.extent = source;
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			boolean hasNext = delegate.hasNext();
			if(!hasNext){
				extent.close(delegate);
			}
			return hasNext();
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public T next() {
			return delegate.next();
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			delegate.remove();
		}
		
	}

}

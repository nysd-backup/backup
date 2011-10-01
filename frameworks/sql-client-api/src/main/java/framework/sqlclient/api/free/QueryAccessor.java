/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

/**
 * The accessor for query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryAccessor {
	
	/**
	 * Set the delegating query.
	 * 
	 * @param <D> the type
	 * @param query the query
	 * @param delegate the delegate 
	 */
	public static <D extends FreeQuery> void setDelegate(AbstractFreeQuery<D> query , D delegate){
		query.setDelegate(delegate);
	}
	

	/**
	 * Set the delegating query.
	 * 
	 * @param <D> the type
	 * @param query the query
	 * @param delegate the delegate 
	 */
	public static <D extends FreeQuery> D getDelegate(AbstractFreeQuery<D> query){
		return query.getDelegate();
	}
	
	/**
	 * Set the delegating query.
	 * 
	 * @param <D> the type
	 * @param query the query
	 * @param delegate the delegate 
	 */
	public static <D extends FreeUpdate> void setDelegate(AbstractUpdate<D> query , D delegate){
		query.setDelegate(delegate);
	}
		
}

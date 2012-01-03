/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import javax.persistence.QueryHint;

import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.free.impl.LocalQueryEngine;
import kosmos.framework.sqlclient.internal.free.impl.LocalUpdateEngine;

/**
 * The factory to create the free writable query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryFactoryImpl implements QueryFactory{
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery, T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass) {
		AnonymousQuery aq = queryClass.getAnnotation(AnonymousQuery.class);		

		FreeQueryParameter parameter = new FreeQueryParameter(aq.resultClass(), false, queryClass.getSimpleName(), aq.query());
		
		K engine = (K)new LocalQueryEngine(internalQuery,parameter);
		for(QueryHint hint : aq.hints()){
			engine.setHint(hint.name(), hint.value());
		}
		T q;
		try {
			q = queryClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		q.setDelegate(engine);
		return q;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeUpdate, T extends AbstractFreeUpdate<K>> T createUpdate(Class<T> updateClass) {
		
		AnonymousQuery aq = updateClass.getAnnotation(AnonymousQuery.class);		
		FreeUpdateParameter parameter = new FreeUpdateParameter(false, updateClass.getSimpleName(), aq.query());

		K engine = (K)new LocalUpdateEngine(internalQuery,parameter);
		for(QueryHint hint : aq.hints()){
			engine.setHint(hint.name(), hint.value());
		}
		T q;
		try {
			q = updateClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		q.setDelegate(engine);
		return q;
	}

}

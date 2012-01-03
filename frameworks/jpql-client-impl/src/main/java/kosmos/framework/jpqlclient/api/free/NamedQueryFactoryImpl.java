/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.QueryHint;

import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedQueryEngine;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedUpdateEngine;
import kosmos.framework.sqlclient.api.Update;
import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.free.AbstractFreeUpdate;
import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.free.QueryAccessor;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.sqlclient.internal.free.InternalQuery;


/**
 * The factory to create the named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NamedQueryFactoryImpl implements QueryFactory{

	/** the internal query */
	private InternalQuery internalNamedQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalNamedQuery(InternalQuery internalNamedQuery) {
		this.internalNamedQuery = internalNamedQuery;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeUpdate,T extends AbstractFreeUpdate<K>> T createUpdate(Class<T> updateClass) {
		K delegate = null;
		
		if( AbstractNamedUpdate.class.isAssignableFrom(updateClass)){			
			delegate = (K)createNamedUpdateEngine(updateClass);
		
		}else if(AbstractNativeUpdate.class.isAssignableFrom(updateClass)){
			delegate = (K)createNativeUpdateEngine(updateClass);
		
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + updateClass);
		}
		T update = newInstance(updateClass);
		QueryAccessor.setDelegate(update,delegate);
		return update;
	}


	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass){
		K delegate = null;
		
		if( AbstractNamedQuery.class.isAssignableFrom(queryClass)){
			delegate = (K)createNamedQueryEngine(queryClass);
		
		}else if(AbstractNativeQuery.class.isAssignableFrom(queryClass)){			
			delegate = (K)createNativeQueryEngine(queryClass);
			
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + queryClass);
		}
		T eq = newInstance(queryClass);
		QueryAccessor.setDelegate(eq, delegate);		
		return eq;
	}
	
	/**
	 * @param queryClass　the class of the query 
	 * @return the query
	 */
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param updateClass　the class of the updater
	 * @return the updater
	 */
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param queryClass　the class of the query
	 * @return the query
	 */
	protected FreeQuery createNamedQueryEngine(Class<?> queryClass){
		FreeQueryParameter parameter = FreeQueryParameter.class.cast( getParameter(queryClass, false));
		return new LocalNamedQueryEngine(internalNamedQuery,parameter);
	}
	
	/**
	 * @param updateClass　the class of the updater
	 * @return the updater
	 */
	protected Update createNamedUpdateEngine(Class<?> updateClass){
		FreeUpdateParameter parameter = FreeUpdateParameter.class.cast( getParameter(updateClass, true));
		return new LocalNamedUpdateEngine(internalNamedQuery,parameter);
	}
	

	/**
	 * Creates the internal named query.
	 * 
	 * @param clazz the class 
	 * @return the query
	 */
	protected FreeParameter getParameter(Class<?> clazz,boolean update){
		
		javax.persistence.NamedQuery nq = clazz.getAnnotation(javax.persistence.NamedQuery.class);
		FreeParameter parameter = null;
		QueryHint[] hints = null;
		//標準
		if(nq != null){
			if(update){
				parameter = new FreeUpdateParameter(false,clazz.getSimpleName(),nq.query());				
			}else{
				parameter = new FreeQueryParameter(null, false,clazz.getSimpleName(),nq.query());	
			}
			parameter.setName(nq.name());
			hints = nq.hints();
		
		//拡張-if文用	
		}else{
			AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);
			if(update){
				parameter = new FreeUpdateParameter(false,clazz.getSimpleName(),aq.query());				
			}else{
				parameter = new FreeQueryParameter(aq.resultClass(), false ,clazz.getSimpleName(), aq.query());	
			}
			hints = aq.hints();
		}
		for(QueryHint h: hints){
			parameter.getHints().put(h.name(), h.value());
		}
		return parameter;
	}
	
	/**
	 * @param <T>　the type
	 * @param clazz the class 
	 * @return the new instance
	 */
	protected <T> T newInstance(Class<T> clazz){
		try{
			return clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}

}

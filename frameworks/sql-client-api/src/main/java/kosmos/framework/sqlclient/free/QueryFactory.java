/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import javax.persistence.QueryHint;

import kosmos.framework.sqlclient.free.strategy.InternalQuery;


/**
 * The factor to create query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryFactory {
	
	/** for native query */
	private InternalQuery internalNativeQuery;

	/** for named query */
	private InternalQuery internalNamedQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalNativeQuery(InternalQuery internalNativeQuery){
		this.internalNativeQuery = internalNativeQuery;
	}
	
	/**
	 * @param internalNamedQuery the internalNamedQuery to set
	 */
	public void setInternalNamedQuery(InternalQuery internalNamedQuery){
		this.internalNamedQuery = internalNamedQuery;
	}
	
	/**
	 * Creates the query.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	public <T extends AbstractFreeQuery> T createQuery(Class<T> clazz){
		T instance = null;
		try{
			instance = clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
				
		AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);
		instance.getParameter().setQueryId(clazz.getSimpleName());
		if(aq != null){
			instance.getParameter().setResultType(aq.resultClass());
			instance.getParameter().setSql(aq.query());
			for(QueryHint hints : aq.hints()){
				instance.setHint(hints.name(), hints.value());
			}
		}
		instance.setInternalQuery(instance instanceof AbstractNamedQuery ? internalNamedQuery :internalNativeQuery);
		return instance;
	}
	
	
	/**
	 * Creates the updater.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	public <T extends AbstractFreeUpdate> T createUpdate(Class<T> clazz){
		T instance = null;
		try{
			instance = clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
		instance.setInternalQuery(instance instanceof AbstractNamedUpdate ? internalNamedQuery :internalNativeQuery);
				
		AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);
		instance.getParameter().setQueryId(clazz.getSimpleName());
		if(aq != null){			
			instance.getParameter().setSql(aq.query());
			for(QueryHint hints : aq.hints()){
				instance.setHint(hints.name(), hints.value());
			}
		}
		return instance;
	}
	
}

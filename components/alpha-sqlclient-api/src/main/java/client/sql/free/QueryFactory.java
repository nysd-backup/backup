/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

import client.sql.free.strategy.InternalQuery;


/**
 * The factory to create query.
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
	public <T extends AbstractFreeReadQuery> T createReadQuery(Class<T> clazz,EntityManager em){
		T instance = null;
		try{
			instance = clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
		instance.getParameter().setQueryId(clazz.getSimpleName());
		instance.getParameter().setEntityManager(em);
		boolean namedQuery = instance instanceof AbstractNamedReadQuery;
		if(namedQuery){
			NamedQuery nq = clazz.getAnnotation(NamedQuery.class);
			if(nq != null){				
				instance.getParameter().setName(nq.name());
				instance.getParameter().setSql(nq.query());
				for(QueryHint hints : nq.hints()){
					instance.setHint(hints.name(), hints.value());
				}				
			}
			instance.setInternalQuery(internalNamedQuery);
		}else{
			instance.setInternalQuery(internalNativeQuery);;
		}		
		return instance;
	}
	
	/**
	 * Creates the updater.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	public <T extends AbstractFreeModifyQuery> T createModifyQuery(Class<T> clazz,EntityManager em){
		T instance = null;
		try{
			instance = clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
		boolean namedQuery = instance instanceof AbstractNamedModifyQuery;
		instance.getParameter().setQueryId(clazz.getSimpleName());
		instance.getParameter().setEntityManager(em);
		if(namedQuery){
			NamedQuery nq = clazz.getAnnotation(NamedQuery.class);
			if(nq != null){	
				instance.getParameter().setName(nq.name());
				instance.getParameter().setSql(nq.query());
				for(QueryHint hints : nq.hints()){
					instance.setHint(hints.name(), hints.value());
				}
				
			}
			instance.setInternalQuery(internalNamedQuery);
		}else{
			instance.setInternalQuery(internalNativeQuery);;
		}		
		return instance;		
	}
	
}

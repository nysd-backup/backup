/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import javax.persistence.EntityManager;

import org.coder.alpha.query.free.gateway.PersistenceGateway;




/**
 * The factory to create query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryFactory {
	
	/** for native query */
	private PersistenceGateway nativeGateway;

	/** for named query */
	private PersistenceGateway jpqlGateway;
	
	/**
	 * @param nativeGateway the nativeGateway to set
	 */
	public void setNativeGateway(PersistenceGateway nativeGateway){
		this.nativeGateway = nativeGateway;
	}
	
	/**
	 * @param jpqlGateway the jpqlGateway to set
	 */
	public void setJpqlGateway(PersistenceGateway jpqlGateway){
		this.jpqlGateway = jpqlGateway;
	}
	
	/**
	 * Creates the query.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	public <T extends AbstractReadQuery> T createReadQuery(Class<T> clazz,EntityManager em){
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
			instance.setPersistenceGateway(jpqlGateway);
		}else{
			instance.setPersistenceGateway(nativeGateway);;
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
	public <T extends AbstractModifyQuery> T createModifyQuery(Class<T> clazz,EntityManager em){
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
			instance.setPersistenceGateway(jpqlGateway);
		}else{
			instance.setPersistenceGateway(nativeGateway);;
		}		
		return instance;		
	}
	
}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.tablegateway.repository;

import javax.persistence.EntityManager;

import org.coder.alpha.framework.registry.EJBComponentFinder;
import org.coder.alpha.framework.registry.ServiceLocator;
import org.coder.alpha.query.criteria.CriteriaQueryFactory;


/**
 * RowdataGatewayFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultTableRepository<T> {

	protected final EntityManager em;
	
	protected Class<T> entityClass;
	
	public DefaultTableRepository(EntityManager em){
		this.em = em;
	}
	
	public void setEntityClass(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public T find(Object pk){
		T entity = (T)em.find(entityClass, pk);
		return entity;
	}
	
	public T create(){
		try {
			return (T)entityClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Persists the entity.
	 * @param entity
	 */
	public void persist(T entity){
		em.persist(entity);
	}
	
	protected CriteriaQueryFactory createCriteriaQueryFactory() {
		EJBComponentFinder finder = ServiceLocator.getComponentFinder();
		return finder.getQueryFactoryFinder().createCriteriaQueryFactory();
	}
	
}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.tableentity;

import javax.persistence.EntityManager;


/**
 * RowdataGatewayFinder.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class TableEntityFinder<T extends TableEntity> {

	private EntityManager em;
	
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public T find(Object pk){
		T entity = em.find(getEntityClass(), pk);
		entity.setEntityManager(em);
		return entity;
	}
	
	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public T create(){
		T entity = null;
		try{
			entity = getEntityClass().newInstance();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		entity.setEntityManager(em);
		return entity;
	}
	
	/**
	 * Gets the entity class
	 */
	protected abstract Class<T> getEntityClass();
}

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
public class TableEntityFinder<T extends AbstractTableEntity> {

	private final EntityManager em;
	
	private final Class<T> entityClass;
	
	public TableEntityFinder(EntityManager em , Class<T> entityClass){
		this.em = em;
		this.entityClass = entityClass;
	}
	
	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public T find(Object pk){
		T entity = em.find(entityClass, pk);
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
			entity = entityClass.newInstance();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		entity.setEntityManager(em);
		return entity;
	}
}

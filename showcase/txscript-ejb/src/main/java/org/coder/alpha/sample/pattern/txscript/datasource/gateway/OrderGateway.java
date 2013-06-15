/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.txscript.datasource.gateway;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.sample.common.Traceable;
import org.coder.alpha.sample.pattern.txscript.datasource.entity.Order;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class OrderGateway {
	
	@PersistenceContext
	protected EntityManager em;

	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public Order find(long pk){
		Order entity = em.find(Order.class,pk);
		return entity;
	}
	
	/**
	 * @return
	 */
	public Order create(){
		return new Order();
	}
	
	/**
	 * Persists the entity.
	 * @param entity
	 */
	public void persist(Order entity){
		em.persist(entity);
	}
	
}

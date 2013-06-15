/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.txscript.datasource.gateway;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.sample.common.Traceable;
import org.coder.alpha.sample.pattern.txscript.datasource.entity.OrderDetail;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class OrderDetailGateway {
	
	@PersistenceContext
	protected EntityManager em;

	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public OrderDetail find(long pk){
		OrderDetail entity = em.find(OrderDetail.class,pk);
		return entity;
	}
	
	/**
	 * @return
	 */
	public OrderDetail create(){
		return new OrderDetail();
	}
	
	/**
	 * Persists the entity.
	 * @param entity
	 */
	public void persist(OrderDetail entity){
		em.persist(entity);
	}
	
}

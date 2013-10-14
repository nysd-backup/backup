/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.txscriptejb.datasource.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.txscriptejb.datasource.entity.Order;
import org.coder.alpha.txscriptejb.interceptor.Traceable;
import org.coder.gear.query.QueryFactoryFinder;
import org.coder.gear.query.criteria.CriteriaQueryFactory;
import org.coder.gear.query.criteria.query.ListReadQuery;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Traceable
public class OrderGateway {
	
	@PersistenceContext
	protected EntityManager em;
	
	private CriteriaQueryFactory criteriaFactory = new QueryFactoryFinder().createCriteriaQueryFactory();
	
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

	/**
	 * @param customerCd
	 * @return
	 */
	public List<Order> getResultByCustomerCd(String customerCd){
		ListReadQuery<Order> order = criteriaFactory.createListReadQuery(Order.class, em);
		order.eq("customerCd", customerCd);
		return order.call();
	}
	
}

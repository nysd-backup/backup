/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.datasource.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.framework.registry.DefaultQueryFactoryFinder;
import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.txscriptejb.datasource.entity.Order;
import org.coder.alpha.txscriptejb.interceptor.Traceable;


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
	
	private CriteriaQueryFactory criteriaFactory = new DefaultQueryFactoryFinder().createCriteriaQueryFactory();
	
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
		order.eq(Order.CUSTOMER_CD, customerCd);
		return order.call();
	}
	
}

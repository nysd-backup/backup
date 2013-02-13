/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.tablegateway.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.txscriptejb.tablegateway.entity.Order;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderRepository extends DefaultTableRepository<Order> {

	public OrderRepository(EntityManager em) {
		super(em);		
		setEntityClass(Order.class);
	}

	/**
	 * @param customerCd
	 * @return
	 */
	public List<Order> getResultByCustomerCd(String customerCd){
		ListReadQuery<Order> order = createCriteriaQueryFactory().createListReadQuery(Order.class, em);
		order.eq(Order.CUSTOMER_CD, customerCd);
		return order.call();
	}
	
}

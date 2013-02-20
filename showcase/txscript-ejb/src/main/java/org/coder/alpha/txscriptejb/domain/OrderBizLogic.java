/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.coder.alpha.txscriptejb.datasource.entity.Order;
import org.coder.alpha.txscriptejb.datasource.gateway.OrderGateway;


/**
 * OrderBizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderBizLogic{
	
	@Inject
	private OrderGateway repository;
	
	/**
	 * Persist
	 * @param orderNo
	 * @return
	 */
	public void persist(String orderNo){
		assert orderNo != null;
		Order order = repository.find(new Long(orderNo));
		if(order == null){
			order = repository.create();
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
			repository.persist(order);
		}else{
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
		}
	}
	
	/**
	 * Persist
	 * @param orderNo
	 * @return
	 */
	public List<Order> search(String customerCd){
		assert customerCd != null;
		return repository.getResultByCustomerCd(customerCd);		
	}
	
}

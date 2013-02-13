/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import java.util.Date;

import javax.ejb.Stateless;

import org.coder.alpha.txscriptejb.tablegateway.entity.Order;
import org.coder.alpha.txscriptejb.tablegateway.repository.OrderRepository;


/**
 * OrderBizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class OrderBizLogic extends BizLogic{
	
	/**
	 * Persist
	 * @param orderNo
	 * @return
	 */
	public void persist(String orderNo){
		OrderRepository repository = createRepository(OrderRepository.class);
		Order order = repository.find(orderNo);
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
	
}

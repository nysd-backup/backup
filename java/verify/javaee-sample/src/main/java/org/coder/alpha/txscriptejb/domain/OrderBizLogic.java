/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.txscriptejb.domain;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.coder.alpha.txscriptejb.datasource.entity.Order;
import org.coder.alpha.txscriptejb.datasource.gateway.OrderGateway;
import org.coder.alpha.txscriptejb.interceptor.Traceable;


/**
 * OrderBizLogic.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Traceable
public class OrderBizLogic{
	
	/** デフォルトだとOrderBizLogicと同じライフサイクルになる */
	@Inject
	private OrderGateway gateway;
	
	/**
	 * Persist
	 * @param orderNo
	 * @return
	 */
	public void persist(String orderNo){
		assert orderNo != null;
		Order order = gateway.find(new Long(orderNo));
		if(order == null){
			order = gateway.create();
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
			gateway.persist(order);
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
		return gateway.getResultByCustomerCd(customerCd);		
	}
	
}

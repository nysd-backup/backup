/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import java.util.Date;

import javax.ejb.Stateless;

import org.coder.alpha.txscriptejb.tableentity.Order;
import org.coder.alpha.txscriptejb.tableentity.OrderFinder;


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
		OrderFinder finder = createTableFinder(OrderFinder.class);
		Order order = finder.find(orderNo);
		if(order == null){
			order = finder.create();
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
			order.persist();
		}else{
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
		}
	}
	
}

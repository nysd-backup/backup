/**
 * 
 */
package org.coder.gear.sample.javaee7.factory;

import java.util.Date;

import org.coder.gear.sample.javaee7.domain.entity.Order;
import org.coder.gear.sample.javaee7.domain.entity.OrderDetail;

/**
 * @author yoshida-n
 *
 */
public class OrderFactory extends AbstractFactory<Order>{

	/**
	 * @see org.coder.gear.sample.javaee7.factory.AbstractFactory#createFrom(org.coder.gear.sample.javaee7.domain.entity.AbstractEntity)
	 */
	@Override
	public Order createFrom(Order dto){
		Order domainRoot = new Order();
		domainRoot.orderDt = new Date();
		domainRoot.no = dto.no;
		domainRoot.customerCd = "CST1";
		OrderDetail detail = new OrderDetail();		
		detail.count = 1L;
		detail.slsPrice = 100L;	
		detail.detailNo = 1L;
		detail.order = domainRoot;	//JPAの場合これを使用しないと子テーブルのFKに設定されない
		domainRoot.orderDetails.add(detail);
		return domainRoot;
	}
}

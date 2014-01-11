/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.coder.gear.message.Message;
import org.coder.gear.message.MessageContext;
import org.coder.gear.sample.javaee7.domain.Order;
import org.coder.gear.sample.javaee7.domain.OrderDetail;
import org.coder.gear.sample.javaee7.domain.Stock;
import org.coder.gear.sample.javaee7.repository.OrderRepository;
import org.coder.gear.sample.javaee7.repository.StockRepository;
import org.coder.gear.trace.Traceable;

/**
 * OrderOperationService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class OrderOperationService {

	/** デフォルトだとOrderOperationServiceと同じライフサイクルになる */
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private StockRepository stockRepository;
	
	/**
	 * @param order
	 */
	public void order(Order order){
		
		//在庫引き当て
		for(OrderDetail e : order.orderDetails){
			Stock stock = stockRepository.find(e.itemNo);
			if(stock.canReserve(e.count)){
				stock.reserve(e.count);
			}else {
				Message msg = new Message();
				msg.setMessage("error :" + e.itemNo);
				MessageContext.getCurrentInstance().addMessage(msg);
			}
		}
		
		//注文
		orderRepository.persist(order);
		
	}
	
	/**
	 * @param orderNo
	 */
	public void cancel(Long orderNo, Long version){
		
		//検索
		Order order = orderRepository.find(orderNo);		
		
		//引き当て戻す
		for(OrderDetail e : order.orderDetails){
			Stock stock = stockRepository.find(e.itemNo);
			stock.cancel(e.count);			
		}
		
		//キャンセル
		order.version = version;
		orderRepository.remove(order);
		
	}
	
}

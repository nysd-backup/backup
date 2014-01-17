/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.application;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.coder.gear.message.Message;
import org.coder.gear.message.MessageContext;
import org.coder.gear.sample.javaee7.domain.entity.Order;
import org.coder.gear.sample.javaee7.domain.entity.OrderDetail;
import org.coder.gear.sample.javaee7.domain.entity.Stock;
import org.coder.gear.sample.javaee7.domain.repository.OrderRepository;
import org.coder.gear.sample.javaee7.domain.repository.StockRepository;
import org.coder.gear.sample.javaee7.factory.OrderFactory;
import org.coder.gear.sample.javaee7.factory.StockFactory;
import org.coder.gear.trace.Traceable;

/**
 * OrderOperationService.
 * 
 * アプリケーション層　外部のドメインを呼び出す場合には相手先はInterfaceの方が疎結合になる。
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class OrderOperationService {

	//'@InjectするとOrderOperationServiceと同じライフサイクルになる。
	
	@Inject
	private OrderFactory orderFactory;
	
	@Inject
	private StockFactory stockFactory;
	
	/** 
	 * interface宣言しているが実際DataAccess方法が変わることはないのでアプリケーション層にDIするなら実体でもよいと思う。
	 *　インターセプターはさめないわけでもないし。
	 */
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private StockRepository stockRepository;
	
	/**
	 * @param order ここではEntityをDTOにしている
	 */
	public void order(Order dto){
		
		Order domainObject = orderFactory.createFrom(dto);
		
		//在庫引き当て
		for(OrderDetail e : domainObject.orderDetails){
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
		orderRepository.persist(domainObject);
		
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

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
import org.coder.gear.sample.javaee7.infra.repository.OrderRepository;
import org.coder.gear.sample.javaee7.infra.repository.StockRepository;
import org.coder.gear.trace.Traceable;

/**
 * OrderOperationService.
 * アプリケーションサービス兼/ドメインサービス　外部のドメインを呼び出す場合には相手先はInterfaceの方が疎結合になる。
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class OrderOperationService {

	/** 
	 * '@IntejectするとデフォルトだとOrderOperationServiceと同じライフサイクルになる
	 *	教科書どおりだとRepositoryはIFにしているが実際DataAccess方法が変わることはないので実体でよい。インターセプターはさめないわけでもないし。
	 */
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

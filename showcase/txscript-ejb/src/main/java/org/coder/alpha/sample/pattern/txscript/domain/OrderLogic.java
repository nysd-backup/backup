/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.txscript.domain;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.criteria.query.DeleteQuery;
import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.sample.common.Traceable;
import org.coder.alpha.sample.pattern.txscript.datasource.entity.Order;
import org.coder.alpha.sample.pattern.txscript.datasource.entity.OrderDetail;
import org.coder.alpha.sample.pattern.txscript.datasource.gateway.OrderDetailGateway;
import org.coder.alpha.sample.pattern.txscript.datasource.gateway.OrderGateway;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class OrderLogic {
	
	@PersistenceContext
	protected EntityManager em;

	/** デフォルトだとOrderBizLogicと同じライフサイクルになる */
	@Inject
	private OrderGateway gateway;
	
	@Inject
	private OrderDetailGateway detailGateway;
	
	@Resource
	private SessionContext context;
	
	/**
	 * Persist.
	 * @param orderNo
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
			
			//明細
			OrderDetail detail = detailGateway.create();
			detail.setOrderNo(new Long(orderNo));
			detail.setDetailNo(System.currentTimeMillis());
			detailGateway.persist(detail);
			
		}else{
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
			
			//明細洗い替え
			DeleteQuery<Order> query = new CriteriaQueryFactory().createDeleteQuery(Order.class, em);	
			query.call();
			OrderDetail detail = detailGateway.create();
			detail.setOrderNo(new Long(orderNo));
			detail.setDetailNo(System.currentTimeMillis());
			detailGateway.persist(detail);
			
		}
	}
	
	/**
	 * Search.
	 * @param orderNo
	 */
	public List<Order> search(String customerCd){
		ListReadQuery<Order> query = new CriteriaQueryFactory().createListReadQuery(Order.class, em);	
		return query.call();
	}
	
	/**
	 * Error.
	 * @param orderNo
	 */
	public List<Order> error(String customerCd){
		context.setRollbackOnly();
		persist(customerCd);
		em.flush();
		return null;
	}
}

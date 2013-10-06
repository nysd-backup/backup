/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package alpha.domain.spring.example.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;
import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.criteria.query.DeleteQuery;
import org.coder.alpha.query.free.QueryFactory;
import org.coder.alpha.query.free.query.NativeReadQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import alpha.domain.spring.example.dto.OrderDtlDto;
import alpha.domain.spring.example.dto.OrderDto;
import alpha.domain.spring.example.entity.Order;
import alpha.domain.spring.example.entity.OrderDtl;

/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private QueryFactory queryFactory;
	
	@Autowired
	private CriteriaQueryFactory criteriaFactory;
	
	@PersistenceContext(unitName="default")
	private EntityManager em;
	
	/**
	 * 一覧検索
	 * @param dto
	 * @return
	 */
	public OrderDto searchOrder(Long orderNo){
		NativeReadQuery query = queryFactory.createReadQuery(NativeReadQuery.class, em);
		query.setQueryId("searchOrderDtl.sql");
		query.set(Order.ORDER_NO.name(), orderNo).setResultType(OrderDtlDto.class);
		List<OrderDtlDto> resultList = query.getResultList();
		OrderDto response = new OrderDto();
		response.setOrderNo(orderNo);
		response.setDetailDto(resultList);
		return response;
	}
	
	/**
	 * 登録/更新
	 * @param dto DTO
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void createOrder(OrderDto dto) throws IllegalAccessException, InvocationTargetException{
		
		//登録	
		if(dto.getVersion() == null){
			Order order = null;
			order.setOrderNo(System.currentTimeMillis());
			em.persist(order);
		//更新	
		}else {
			
			//明細削除
			DeleteQuery<OrderDtl> query = criteriaFactory.createDeleteQuery(OrderDtl.class, em);
			query.eq(OrderDtl.ORDER_NO, dto.getOrderNo()).call();
			
			//ヘッダ更新
			Order order = em.find(Order.class, dto.getOrderNo());
			BeanUtils.copyProperties(dto, order);
			
			//明細登録
			if(dto.getDetailDto() != null){
				long i = 0;
				for(OrderDtlDto dtl : dto.getDetailDto()){
					OrderDtl detail = null;
					detail.setDetailNo(i);
					i++;
				}
			}
		}
	}
}

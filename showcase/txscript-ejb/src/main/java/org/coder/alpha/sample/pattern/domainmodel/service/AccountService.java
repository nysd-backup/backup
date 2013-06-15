/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.domainmodel.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.coder.alpha.sample.common.Traceable;
import org.coder.alpha.sample.pattern.domainmodel.domain.Account;
import org.coder.alpha.sample.pattern.domainmodel.repository.AccountRepository;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class AccountService {
	
	/** デフォルトだとOrderBizLogicと同じライフサイクルになる */
	@Inject
	private AccountRepository repositry;
	
	/**
	 * Persist.
	 * @param orderNo
	 */
	public void persist(String orderNo){
		assert orderNo != null;
		Account order = repositry.find(new Long(orderNo));
		if(order == null){
			order = Account.create();
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
			repositry.persist(order);
			
		}else{
			order.setCustomerCd("aaa");
			order.setOrderDt(new Date());
			order.setOrderNo(new Long(orderNo));
		}
	}
	
	/**
	 * Search.
	 * @param orderNo
	 */
	public List<Account> search(String customerCd){
		return repositry.findByCustomer(customerCd);
	}
	
}

/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.coder.alpha.txscriptejb.domain.OrderBizLogic;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class OrderService {

	@Inject
	private OrderBizLogic bizLogic;
	
	/**
	 * Persist.
	 * @param orderNo
	 */
	public void persist(String orderNo){
		bizLogic.persist(orderNo);
	}
}

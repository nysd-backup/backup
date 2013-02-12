/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.coder.alpha.txscriptejb.domain.OrderBizLogic;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class OrderService {

	@EJB
	private OrderBizLogic bizLogic;
	
	/**
	 * Persist.
	 * @param orderNo
	 */
	public void persist(String orderNo){
		bizLogic.persist(orderNo);
	}
}

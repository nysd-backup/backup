/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.coder.alpha.txscriptejb.domain.OrderBizLogic;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Path("/order")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
@Stateless
public class OrderService {

	@EJB
	private OrderBizLogic bizLogic;
	
	/**
	 * Persist.
	 * @param orderNo
	 */
	@GET
	@Path("/persist")
	public void persist(String orderNo){
		bizLogic.persist(orderNo);
	}
}

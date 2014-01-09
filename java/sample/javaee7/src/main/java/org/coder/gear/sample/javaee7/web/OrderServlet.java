/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.web;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coder.gear.sample.javaee7.domain.Order;
import org.coder.gear.sample.javaee7.domain.OrderDetail;
import org.coder.gear.sample.javaee7.service.OrderOperationService;

/**
 * OrderBean.
 *
 * @author yoshida-n
 * @version	created.
 */
@WebServlet(name = "orderServlet", urlPatterns = { "/order" })
public class OrderServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Inject	
	private OrderOperationService service;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req , HttpServletResponse res){
		java.util.logging.Logger.getLogger("aa").info("サービス結果 = " + (service == null));
		if(req.getParameter("order") != null){
			String no = req.getParameter("order");
			Order order = new Order();
			order.no = Long.parseLong(no);
			OrderDetail d = new OrderDetail();
			d.itemNo = 100L;
			d.slsPrice = 2000L;
			d.detailNo = 1L;
			order.orderDetails.add(d);
			service.order(order);
		}else if(req.getParameter("cancel") != null){
			service.cancel(Long.parseLong(req.getParameter("cancel")), Long.parseLong(req.getParameter("version")));
		}
	}

}

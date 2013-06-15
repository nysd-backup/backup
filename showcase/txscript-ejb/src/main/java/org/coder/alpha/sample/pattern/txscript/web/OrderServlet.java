/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.txscript.web;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coder.alpha.sample.pattern.txscript.domain.OrderLogic;

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
	private OrderLogic service;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req , HttpServletResponse res){
		if("error".equals(req.getParameter("view"))){
			service.error(req.getParameter("orderNo"));
		}else if("search".equals(req.getParameter("view"))){
			service.search(req.getParameter("orderNo"));
		}else{
			service.persist(req.getParameter("orderNo"));
		}
	}

}

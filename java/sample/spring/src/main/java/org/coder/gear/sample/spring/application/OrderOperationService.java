/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.application;

import java.util.Date;
import java.util.logging.Logger;

import org.coder.gear.sample.spring.domain.entity.Order;
import org.springframework.stereotype.Service;

/**
 * OrderOperationService.
 * 
 * @author yoshida-n
 * @version	created.
 */
@Service
public class OrderOperationService {

	/**
	 * @param dto
	 */
	public void order(Order dto){
		
		Order domainObject = new Order();
		domainObject.customerCd = dto.customerCd;
		domainObject.no = dto.no;
		domainObject.orderDt = new Date();		
		domainObject.orderDetails = dto.orderDetails;
		
		Logger log = Logger.getLogger("LOG");
		log.info(String.valueOf(domainObject.sum()));
		
		//注文
		domainObject.save();
		
	}
	
}

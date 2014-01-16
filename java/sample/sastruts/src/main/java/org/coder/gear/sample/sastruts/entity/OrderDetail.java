/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.sastruts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;


/**
 * Order.
 *
 * @author yoshida-n
 * @version	created.
 */
@Entity
public class OrderDetail{
	
	@ManyToOne
	public Order order;
	
	@Id
	public Long orderNo;

	@Id
	public Long detailNo;
	
	public Long count;
	
	public Long slsPrice;
	
	public Long itemNo;
	
	@Version
	public Long version;

}
/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * Order.
 *
 * @author yoshida-n
 * @version	created.
 */
@Entity
@Table(name="ORD")
public class Order extends AbstractEntity{
	
	@Id	
	public Long no = null;

	@Column(name="CUSTOMER_CD")
	public String customerCd = null;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ORDER_DT")
	public Date orderDt = null;
	
	@Version
	public Long version = null;
	
	@OneToMany(mappedBy="order",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

	/**
	 * @return
	 */
	public long sum() {
		long sum = 0;
		for(OrderDetail d : orderDetails){
			sum += d.slsPrice;
		}
		return sum;
	}
	/**
	 * @return
	 */
	public long amount() {
		long amnt = 0;
		for(OrderDetail d : orderDetails){
			amnt += d.count;
		}
		return amnt;
	}
	
}

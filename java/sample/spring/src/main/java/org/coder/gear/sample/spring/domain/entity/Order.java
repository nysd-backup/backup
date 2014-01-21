/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.domain.entity;

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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.coder.gear.sample.spring.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Order.
 *
 * @author yoshida-n
 * @version	created.
 */
@Configurable
@Entity
@Table(name="ORD")
public class Order extends AbstractEntity{
	
	//Configurableを指定することでnewした時に自動でinjectされる
	@Autowired(required=false)
	@Transient
	private OrderRepository repository;
	
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
	
	/**
	 * 
	 */
	public void save(){
		repository.persist(this);
	}
}

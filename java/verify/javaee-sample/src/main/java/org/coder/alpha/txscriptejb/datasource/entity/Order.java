/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.datasource.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Order{
	
	@Id
	@Column(name="ORDER_NO")
	private Long orderNo = null;
	
	@Column(name="CUSTOMER_CD")
	private String customerCd = null;
	
	@Column(name="ORDER_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDt = null;
	
	@Column(name="VERSION")
	@Version
	private Long version = null;

	/**
	 * @return the orderNo
	 */
	public Long getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the customerCd
	 */
	public String getCustomerCd() {
		return customerCd;
	}

	/**
	 * @param customerCd the customerCd to set
	 */
	public void setCustomerCd(String customerCd) {
		this.customerCd = customerCd;
	}

	/**
	 * @return the orderDt
	 */
	public Date getOrderDt() {
		return orderDt;
	}

	/**
	 * @param orderDt the orderDt to set
	 */
	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
}

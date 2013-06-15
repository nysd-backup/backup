/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.domainmodel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order.
 *
 * @author yoshida-n
 * @version	created.
 */
@Entity
@Table(name="ORD_DTL")
public class AccountDetail{

	@Id
	private Long orderNo;
	
	@Id
	private Long detailNo;

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
	 * @return the detailNo
	 */
	public Long getDetailNo() {
		return detailNo;
	}

	/**
	 * @param detailNo the detailNo to set
	 */
	public void setDetailNo(Long detailNo) {
		this.detailNo = detailNo;
	} 
	

}

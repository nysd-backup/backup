/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.domainmodel.domain;

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
@Table(name="ACCOUNT")
public class Account{

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
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
	private List<AccountDetail> detail;

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

	/**
	 * @return the detail
	 */
	public List<AccountDetail> getDetail() {
		return detail;
	}
	
	/**
	 * @param detail
	 * @return
	 */
	public boolean addDetail(AccountDetail detail){
		for(AccountDetail d : getDetail()){
			String key = d.getDetailNo() + ":" + d.getOrderNo();
			if(key.equals(detail.getDetailNo() + ":" + detail.getOrderNo())){
				return false;
			}
		}
		this.detail.add(detail);
		return true;
	}
	
	/**
	 * @return
	 */
	public static Account create() {
		return new Account();
	}
	
	/**
	 * @param object
	 * @return
	 */
	public static Account createFrom(Object object) {
		Account accnt = new Account();
		//TODO reflection
		return accnt;
	}
}

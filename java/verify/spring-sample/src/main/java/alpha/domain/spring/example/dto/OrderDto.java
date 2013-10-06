/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package alpha.domain.spring.example.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
 */
@XmlRootElement
public class OrderDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long orderNo = null;
	
	@NotNull
	@Max(10)
	private String customerCd = null;
	
	private Date orderDt = null;
	
	private Long version = null;
	
	private Map<Long,String> versionMap = new LinkedHashMap<Long,String>();
	
	private String viewStatus = "0";
	
	@Valid
	private List<OrderDtlDto> detailDto = new ArrayList<OrderDtlDto>();

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
	 * @return the detailDto
	 */
	public List<OrderDtlDto> getDetailDto() {
		return detailDto;
	}

	/**
	 * @param detailDto the detailDto to set
	 */
	public void setDetailDto(List<OrderDtlDto> detailDto) {
		this.detailDto = detailDto;
	}

	/**
	 * @return the viewStatus
	 */
	public String getViewStatus() {
		return viewStatus;
	}

	/**
	 * @param viewStatus the viewStatus to set
	 */
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	/**
	 * @return the versionMap
	 */
	public Map<Long,String> getVersionMap() {
		return versionMap;
	}

	/**
	 * @param versionMap the versionMap to set
	 */
	public void setVersionMap(Map<Long,String> versionMap) {
		this.versionMap = versionMap;
	}
	
}

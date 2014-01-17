/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderDtlDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long orderNo = null;
	
	private String detailNo = null;
	
	@Size(min=1)
	private String itemCode = null;
	
	private Long count = null;
	
	private Long version = null;
	
	private List<CheckBoxModel> checkBoxes = new ArrayList<CheckBoxModel>();


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
	public String getDetailNo() {
		return detailNo;
	}

	/**
	 * @param detailNo the detailNo to set
	 */
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
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
	 * @return the checkBoxes
	 */
	public List<CheckBoxModel> getCheckBoxes() {
		return checkBoxes;
	}

	/**
	 * @param checkBoxes the checkBoxes to set
	 */
	public void setCheckBoxes(List<CheckBoxModel> checkBoxes) {
		this.checkBoxes = checkBoxes;
	}

}

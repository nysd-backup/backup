/**
 * Copyright 2011 the original author
 */
package alpha.domain.spring.example.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderDtlDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_NO")
	private Long orderNo = null;
	
	@Id
	@Column(name="DETAIL_NO")
	private String detailNo = null;
	
	@Column(name="ITEM_CODE")
	@Size(min=1)
	private String itemCode = null;
	
	@Column(name="COUNT")
	private Long count = null;
	
	@Column(name="VERSION")
	@Version
	private Long version = null;
	
	@Transient
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

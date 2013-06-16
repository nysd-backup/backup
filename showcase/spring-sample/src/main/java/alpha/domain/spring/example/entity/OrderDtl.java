/**
 * Copyright 2011 the original author
 */
package alpha.domain.spring.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.coder.alpha.query.criteria.Metadata;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Entity
@Table(name="ORDER_DTL")
public class OrderDtl {

	public static final Metadata<OrderDtl,Long> ORDER_NO = new Metadata<OrderDtl,Long>("orderNo");
	
	public static final Metadata<OrderDtl,Long> DETAIL_NO = new Metadata<OrderDtl,Long>("detailNo");
	
	public static final Metadata<OrderDtl,String> ITEM_CODE = new Metadata<OrderDtl,String>("itemCode");
	
	public static final Metadata<OrderDtl,Long> COUNT = new Metadata<OrderDtl,Long>("count");
	
	public static final Metadata<OrderDtl,Long> VERSION = new Metadata<OrderDtl,Long>("version");
	
	@Id
	@Column(name="ORDER_NO")
	private Long orderNo = null;
	
	@Id
	@Column(name="DETAIL_NO")
	private Long detailNo = null;
	
	@Column(name="ITEM_CODE")
	private String itemCode = null;
	
	@Column(name="COUNT")
	private Long count = null;
	
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

}

/**
 * Copyright 2011 the original author
 */
package alpha.domain.spring.example.entity;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrderDtlPK {

	private Long orderNo = null;
	
	private Long detailNo = null;

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
/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Order.
 *
 * @author yoshida-n
 * @version	created.
 */
@IdClass(OrderDetailPK.class)
@Entity
@Table(name="ORDER_DETAIL")
public class OrderDetail extends AbstractEntity{
	
	/**
	 * JPAの場合、S2JDBCと異なりFKはいらない。
	 */
	
	//関連先の主キーを主キーに含める場合は関連に@Idつける
	@Id			
	@ManyToOne
	public Order order;
	
	@Id
	@Column(name="DETAIL_NO")
	public Long detailNo;
	
	public Long count;
	
	@Column(name="SLS_PRICE")
	public Long slsPrice;
	
	@Column(name="ITEM_NO")
	public Long itemNo;
	
	@Version
	public Long version;

}
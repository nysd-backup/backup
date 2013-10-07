/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Order {
	
	@Id
	@GeneratedValue
	@Field(name="_id")
	private String id;
	@Basic
	private String description;
	@Basic
	private double totalCost = 0;
	@Embedded
	private Address billingAddress;
	@Embedded
	private Address shippingAddress;
	@ElementCollection
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}

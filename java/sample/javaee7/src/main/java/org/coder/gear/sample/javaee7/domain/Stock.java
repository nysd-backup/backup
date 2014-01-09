/**
 * 
 */
package org.coder.gear.sample.javaee7.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author yoshida-n
 *
 */
@Entity
public class Stock {
	
	@Id
	public Long itemNo;
	
	public Long reserved;
	
	public Long reservable;

	@Version
	public Long version;
	
	/**
	 * @param count
	 * @return
	 */
	public boolean canReserve(Long count) {
		return reservable > count;
	}
	
	/**
	 * @param count
	 */
	public void reserve(Long count){
		reserved += count;
		reservable -= count;
	}
	
	/**
	 * @param count
	 */
	public void cancel(Long count){
		reserved -= count;
		reservable += count;
	}
	
	/**
	 * @param count
	 */
	public void deliver(Long count){
		reserved -= count;
	}
}
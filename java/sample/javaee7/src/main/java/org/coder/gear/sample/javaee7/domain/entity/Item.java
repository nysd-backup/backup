/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author yoshida-n
 *
 */
@Entity
public class Item {
	
	@Id
	@GeneratedValue
	public Long no;
	
	public String name;
	
	public String stdPrice;
	
	public String cost;

	@Version
	public Long version;
}
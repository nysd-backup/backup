/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * @author yoshida-n
 *
 */
@Entity
public class Item extends AbstractEntity{
	
	@Id
	public Long no;
	
	public String name;
	
	public String stdPrice;
	
	public String cost;

	@Version
	public Long version;
	
	@OneToMany(mappedBy="item")
	public List<Stock> stock;
}
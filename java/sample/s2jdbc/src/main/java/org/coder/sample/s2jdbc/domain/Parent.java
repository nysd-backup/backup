/**
 * 
 */
package org.coder.sample.s2jdbc.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author yoshida-n
 *
 */
@Entity
public class Parent {
	
	@Id
	public String id;
	
	public String attr;
	
	@OneToMany(mappedBy="parent")
	public List<Child> child = new ArrayList<Child>();

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}

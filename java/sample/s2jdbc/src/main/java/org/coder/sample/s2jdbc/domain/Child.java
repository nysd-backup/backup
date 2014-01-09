package org.coder.sample.s2jdbc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author yoshida-n
 *
 */
@Entity
public class Child{
	
	@ManyToOne
	public Parent parent;
	
	@Id
	public String parentId;

	@Id
    public String childId;

    public String childAttr;
 
    /**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

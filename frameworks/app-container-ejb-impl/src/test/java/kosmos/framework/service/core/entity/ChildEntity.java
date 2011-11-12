/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import kosmos.framework.core.entity.AbstractEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="child3")
public class ChildEntity extends AbstractEntity{
	
	@Id
	@Column
	private String pk;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Version
	@Column
	private int version;
	
	@ManyToOne
	@JoinColumn(name="TEST")
	private ParentEntity parent;
	
	/**
	 * @param attr the attr to set
	 */
	public ChildEntity setAttr(String attr) {
		this.attr = attr;
		return this;
	}

	/**
	 * @return the atstr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public ChildEntity setAttr2(int attr2) {
		this.attr2 = attr2;
		return this;
	}

	/**
	 * @return the attr2
	 */
	public int getAttr2() {
		return attr2;
	}

	/**
	 * @param version the version to set
	 */
	public ChildEntity setVersion(int version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param pk the pk to set
	 */
	public ChildEntity setPk(String pk) {
		this.pk = pk;
		return this;
	}

	/**
	 * @return the pk
	 */
	public String getPk() {
		return pk;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the parent
	 */
	public ParentEntity getParent() {
		return parent;
	}

}

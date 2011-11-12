/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="CHILD_A")
public class ChildAEntity extends AbstractEntity{
	
	@Id
	@Column
	private String test;
	
	@Id
	@Column
	private String parent_test;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Version
	@Column
	private int version;
	
	
	/**
	 * @param attr the attr to set
	 */
	public ChildAEntity setAttr(String attr) {
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
	public ChildAEntity setAttr2(int attr2) {
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
	public ChildAEntity setVersion(int version) {
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
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param parent_test the parent_test to set
	 */
	public void setParent_test(String parent_test) {
		this.parent_test = parent_test;
	}

	/**
	 * @return the parent_test
	 */
	public String getParent_test() {
		return parent_test;
	}

}

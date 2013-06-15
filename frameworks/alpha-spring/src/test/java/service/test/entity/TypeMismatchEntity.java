/**
 * Copyright 2011 the original author
 */
package service.test.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="typemismatch")
public class TypeMismatchEntity{
	
	@Id
	@Column
	private String test;

	@Column
	private String attr;
	
	@Column
	private String attr2;
	
	@Version
	@Column
	private int version;

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the attr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}

	/**
	 * @return the attr2
	 */
	public String getAttr2() {
		return attr2;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	
	
}

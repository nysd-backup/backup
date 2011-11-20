/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="testB")
public class DateEntity{
	
	@Id
	@Column
	private String test;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Column
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date dateCol;
	
	@Version
	@Column
	private int version;
	
	/**
	 * @param test the test to set
	 */
	public DateEntity setTest(String test) {
		this.test = test;
		return this;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param attr the attr to set
	 */
	public DateEntity setAttr(String attr) {
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
	public DateEntity setAttr2(int attr2) {
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
	public DateEntity setVersion(int version) {
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
	 * @param dateCol the dateCol to set
	 */
	public void setDateCol(Date dateCol) {
		this.dateCol = dateCol;
	}

	/**
	 * @return the dateCol
	 */
	public Date getDateCol() {
		return dateCol;
	}
}

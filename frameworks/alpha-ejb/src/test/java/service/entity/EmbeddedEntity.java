/**
 * Copyright 2011 the original author
 */
package service.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="embedded_test")
public class EmbeddedEntity{

	@EmbeddedId
	private EmbeddPK primaryKeys;

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
	public EmbeddedEntity setAttr(String attr) {
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
	public EmbeddedEntity setAttr2(int attr2) {
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
	public EmbeddedEntity setVersion(int version) {
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
	 * @return the primaryKeys
	 */
	public EmbeddPK getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @param primaryKeys the primaryKeys to set
	 */
	public void setPrimaryKeys(EmbeddPK primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

}

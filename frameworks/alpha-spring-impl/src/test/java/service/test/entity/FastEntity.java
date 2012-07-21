/**
 * Copyright 2011 the original author
 */
package service.test.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import client.sql.orm.Pair;




/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="fast")
public class FastEntity implements client.sql.orm.FastEntity{

	@Id
	@Column
	private String test;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Version
	@Column
	private int version;
	
	/**
	 * @param test the test to set
	 */
	public FastEntity setTest(String test) {
		this.test = test;
		return this;
	}

	/**
	 * @return the test
	 */
	@Id
	@Column
	public String getTest() {
		return test;
	}

	/**
	 * @param attr the attr to set
	 */
	public FastEntity setAttr(String attr) {
		this.attr = attr;
		return this;
	}

	/**
	 * @return the atstr
	 */
	@Column
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public FastEntity setAttr2(int attr2) {
		this.attr2 = attr2;
		return this;
	}

	/**
	 * @return the attr2
	 */
	@Column
	public int getAttr2() {
		return attr2;
	}

	/**
	 * @param version the version to set
	 */
	public FastEntity setVersion(int version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column
	public int getVersion() {
		return version;
	}
	
	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public FastEntity clone(){
		FastEntity entity = new FastEntity();
		entity.test = test;
		entity.attr = attr;
		entity.attr2 = attr2;
		entity.version = version;
		return entity;
	}

	/**
	 * @see client.sql.orm.FastEntity#getVersioningValue()
	 */
	@Override
	public Pair<String> toVersioningValue() {
		return new Pair<String>("version",version);
	}

	/**
	 * @see client.sql.orm.FastEntity#getPrimaryKeys()
	 */
	@Override
	public Map<String, Object> toPrimaryKeys() {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("test", test);
		return map;
	}

	/**
	 * @see client.sql.orm.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("attr", attr);
		map.put("attr2", attr2);
		map.put("version", version);
		return map;
	}
	
}

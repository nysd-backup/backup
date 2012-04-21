/**
 * Copyright 2011 the original author
 */
package kosmos.showcase.entity;

import java.util.Map;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.sqlclient.orm.Metadata;
import kosmos.framework.sqlclient.orm.Pair;


/**
 * 自動生成エンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="GENERATED")
public class GeneratedEntity extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** PK */
	public static final Metadata<GeneratedEntity, java.lang.String> PK = new Metadata<GeneratedEntity, java.lang.String>("PK");
	
	/** VERSION */
	public static final Metadata<GeneratedEntity, java.lang.Long> VERSION = new Metadata<GeneratedEntity, java.lang.Long>("VERSION");
	
	/** ATTR */
	public static final Metadata<GeneratedEntity, java.lang.String> ATTR = new Metadata<GeneratedEntity, java.lang.String>("ATTR");
	
	/** ATTR2 */
	public static final Metadata<GeneratedEntity, java.lang.Long> ATTR2 = new Metadata<GeneratedEntity, java.lang.Long>("ATTR2");
	
	/** TEST */
	public static final Metadata<GeneratedEntity, java.lang.String> TEST = new Metadata<GeneratedEntity, java.lang.String>("TEST");
		
	
	/**
	 * PK
	 */
	private java.lang.String pk = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;
	/**
	 * TEST
	 */
	private java.lang.String test = null;

	/**
	 * @param pk the pk to set
	 */
	public void setPk(java.lang.String pk){
		this.pk = pk;
	}
	
	/**
	 * @return pk
	 */
	@Id
	@Column(name="PK")	 
	public java.lang.String getPk(){
		return pk;
	}
	
	/**
	 * @param version the version to set
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}
	
	/**
	 * @return version
	 */
	@javax.persistence.Version
	@Column(name="VERSION")	 
	public java.lang.Long getVersion(){
		return version;
	}
	
	/**
	 * @param attr the attr to set
	 */
	public void setAttr(java.lang.String attr){
		this.attr = attr;
	}
	
	/**
	 * @return attr
	 */
	@Column(name="ATTR")	 
	public java.lang.String getAttr(){
		return attr;
	}
	
	/**
	 * @param attr2 the attr2 to set
	 */
	public void setAttr2(java.lang.Long attr2){
		this.attr2 = attr2;
	}
	
	/**
	 * @return attr2
	 */
	@Column(name="ATTR2")	 
	public java.lang.Long getAttr2(){
		return attr2;
	}
	
	/**
	 * @param test the test to set
	 */
	public void setTest(java.lang.String test){
		this.test = test;
	}
	
	/**
	 * @return test
	 */
	@Column(name="TEST")	 
	public java.lang.String getTest(){
		return test;
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.FastEntity#getVersioningValue()
	 */
	@Override
	public Pair<String> toVersioningValue() {				
		return new Pair<String>(VERSION.name(),version);		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.orm.FastEntity#getPrimaryKeys()
	 */
	@Override
	public Map<String, Object> toPrimaryKeys() {
		Map<String,Object> map = createMap();
		map.put(PK.name(),pk);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(VERSION.name(),version);
		map.put(ATTR.name(),attr);
		map.put(ATTR2.name(),attr2);
		map.put(TEST.name(),test);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public GeneratedEntity clone() {
		GeneratedEntity clone = new GeneratedEntity();
		clone.pk = pk;
		clone.version = version;
		clone.attr = attr;
		clone.attr2 = attr2;
		clone.test = test;		
		return clone;
	}
}
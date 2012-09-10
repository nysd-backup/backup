/**
 * Use is subject to license terms.
 */
package generated.entity;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.bean.Pair;
import kosmos.framework.core.query.Metadata;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

/**
 * CHILD_Aエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="CHILD_A")
public class ChildA extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** TEST */
	public static final Metadata<ChildA, java.lang.String> TEST = new Metadata<ChildA, java.lang.String>("TEST");
	
	/** PARENT_TEST */
	public static final Metadata<ChildA, java.lang.String> PARENT_TEST = new Metadata<ChildA, java.lang.String>("PARENT_TEST");
	
	/** ATTR */
	public static final Metadata<ChildA, java.lang.String> ATTR = new Metadata<ChildA, java.lang.String>("ATTR");
	
	/** ATTR2 */
	public static final Metadata<ChildA, java.lang.Long> ATTR2 = new Metadata<ChildA, java.lang.Long>("ATTR2");
	
	/** VERSION */
	public static final Metadata<ChildA, java.lang.Long> VERSION = new Metadata<ChildA, java.lang.Long>("VERSION");
		
	
	/**
	 * TEST
	 */
	private java.lang.String test = null;
	/**
	 * PARENT_TEST
	 */
	private java.lang.String parentTest = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;

	/**
	 * @param test the test to set
	 */
	public void setTest(java.lang.String test){
		this.test = test;
	}
	
	/**
	 * @return test
	 */
	@Id
	@Column(name="TEST")	 
	public java.lang.String getTest(){
		return test;
	}
	
	/**
	 * @param parentTest the parentTest to set
	 */
	public void setParentTest(java.lang.String parentTest){
		this.parentTest = parentTest;
	}
	
	/**
	 * @return parentTest
	 */
	@Id
	@Column(name="PARENT_TEST")	 
	public java.lang.String getParentTest(){
		return parentTest;
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
	 * @see kosmos.framework.sqlclient.api.FastEntity#getVersioningValue()
	 */
	@Override
	public Pair<String> toVersioningValue() {				
		return new Pair<String>(VERSION.name(),version);		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getPrimaryKeys()
	 */
	@Override
	public Map<String, Object> toPrimaryKeys() {
		Map<String,Object> map = createMap();
		map.put(TEST.name(),test);
		map.put(PARENT_TEST.name(),parentTest);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(ATTR.name(),attr);
		map.put(ATTR2.name(),attr2);
		map.put(VERSION.name(),version);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public ChildA clone() {
		ChildA clone = new ChildA();
		clone.test = test;
		clone.parentTest = parentTest;
		clone.attr = attr;
		clone.attr2 = attr2;
		clone.version = version;		
		return clone;
	}
}

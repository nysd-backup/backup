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
 * SAMPLENATIVERESULTエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="SAMPLENATIVERESULT")
public class Samplenativeresult extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** DUMMY */
	public static final Metadata<Samplenativeresult, java.lang.Long> DUMMY = new Metadata<Samplenativeresult, java.lang.Long>("DUMMY");
	
	/** ATTR */
	public static final Metadata<Samplenativeresult, java.lang.String> ATTR = new Metadata<Samplenativeresult, java.lang.String>("ATTR");
	
	/** TEST */
	public static final Metadata<Samplenativeresult, java.lang.String> TEST = new Metadata<Samplenativeresult, java.lang.String>("TEST");
	
	/** VERSION */
	public static final Metadata<Samplenativeresult, java.lang.Long> VERSION = new Metadata<Samplenativeresult, java.lang.Long>("VERSION");
	
	/** ATTR2 */
	public static final Metadata<Samplenativeresult, java.lang.Long> ATTR2 = new Metadata<Samplenativeresult, java.lang.Long>("ATTR2");
		
	
	/**
	 * DUMMY
	 */
	private java.lang.Long dummy = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;
	/**
	 * TEST
	 */
	private java.lang.String test = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;

	/**
	 * @param dummy the dummy to set
	 */
	public void setDummy(java.lang.Long dummy){
		this.dummy = dummy;
	}
	
	/**
	 * @return dummy
	 */
	@Id
	@Column(name="DUMMY")	 
	public java.lang.Long getDummy(){
		return dummy;
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
		map.put(DUMMY.name(),dummy);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(ATTR.name(),attr);
		map.put(TEST.name(),test);
		map.put(VERSION.name(),version);
		map.put(ATTR2.name(),attr2);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public Samplenativeresult clone() {
		Samplenativeresult clone = new Samplenativeresult();
		clone.dummy = dummy;
		clone.attr = attr;
		clone.test = test;
		clone.version = version;
		clone.attr2 = attr2;		
		return clone;
	}
}

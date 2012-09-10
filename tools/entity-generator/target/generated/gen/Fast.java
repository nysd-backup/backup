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
 * FASTエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="FAST")
public class Fast extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** TEST */
	public static final Metadata<Fast, java.lang.String> TEST = new Metadata<Fast, java.lang.String>("TEST");
	
	/** ATTR2 */
	public static final Metadata<Fast, java.lang.Long> ATTR2 = new Metadata<Fast, java.lang.Long>("ATTR2");
	
	/** VERSION */
	public static final Metadata<Fast, java.lang.Long> VERSION = new Metadata<Fast, java.lang.Long>("VERSION");
	
	/** ATTR */
	public static final Metadata<Fast, java.lang.String> ATTR = new Metadata<Fast, java.lang.String>("ATTR");
		
	
	/**
	 * TEST
	 */
	private java.lang.String test = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;

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
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(ATTR2.name(),attr2);
		map.put(VERSION.name(),version);
		map.put(ATTR.name(),attr);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public Fast clone() {
		Fast clone = new Fast();
		clone.test = test;
		clone.attr2 = attr2;
		clone.version = version;
		clone.attr = attr;		
		return clone;
	}
}

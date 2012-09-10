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
 * TESTBエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTB")
public class Testb extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** TEST */
	public static final Metadata<Testb, java.lang.String> TEST = new Metadata<Testb, java.lang.String>("TEST");
	
	/** VERSION */
	public static final Metadata<Testb, java.lang.Long> VERSION = new Metadata<Testb, java.lang.Long>("VERSION");
	
	/** DATECOL */
	public static final Metadata<Testb, java.util.Date> DATECOL = new Metadata<Testb, java.util.Date>("DATECOL");
	
	/** ATTR */
	public static final Metadata<Testb, java.lang.String> ATTR = new Metadata<Testb, java.lang.String>("ATTR");
	
	/** ATTR2 */
	public static final Metadata<Testb, java.lang.Long> ATTR2 = new Metadata<Testb, java.lang.Long>("ATTR2");
		
	
	/**
	 * TEST
	 */
	private java.lang.String test = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;
	/**
	 * DATECOL
	 */
	private java.util.Date datecol = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;

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
	 * @param datecol the datecol to set
	 */
	public void setDatecol(java.util.Date datecol){
		this.datecol = datecol;
	}
	
	/**
	 * @return datecol
	 */
	@Column(name="DATECOL")	 
	public java.util.Date getDatecol(){
		return datecol;
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
		map.put(VERSION.name(),version);
		map.put(DATECOL.name(),datecol);
		map.put(ATTR.name(),attr);
		map.put(ATTR2.name(),attr2);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public Testb clone() {
		Testb clone = new Testb();
		clone.test = test;
		clone.version = version;
		clone.datecol = datecol;
		clone.attr = attr;
		clone.attr2 = attr2;		
		return clone;
	}
}

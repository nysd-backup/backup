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
 * GENERATED_CHILDエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="GENERATED_CHILD")
public class GeneratedChild extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** PK */
	public static final Metadata<GeneratedChild, java.lang.String> PK = new Metadata<GeneratedChild, java.lang.String>("PK");
	
	/** BRANCH_NO */
	public static final Metadata<GeneratedChild, java.lang.Long> BRANCH_NO = new Metadata<GeneratedChild, java.lang.Long>("BRANCH_NO");
	
	/** VERSION */
	public static final Metadata<GeneratedChild, java.lang.Long> VERSION = new Metadata<GeneratedChild, java.lang.Long>("VERSION");
	
	/** ATTR2 */
	public static final Metadata<GeneratedChild, java.lang.Long> ATTR2 = new Metadata<GeneratedChild, java.lang.Long>("ATTR2");
	
	/** ATTR */
	public static final Metadata<GeneratedChild, java.lang.String> ATTR = new Metadata<GeneratedChild, java.lang.String>("ATTR");
	
	/** TEST */
	public static final Metadata<GeneratedChild, java.lang.String> TEST = new Metadata<GeneratedChild, java.lang.String>("TEST");
		
	
	/**
	 * PK
	 */
	private java.lang.String pk = null;
	/**
	 * BRANCH_NO
	 */
	private java.lang.Long branchNo = null;
	/**
	 * VERSION
	 */
	private java.lang.Long version = null;
	/**
	 * ATTR2
	 */
	private java.lang.Long attr2 = null;
	/**
	 * ATTR
	 */
	private java.lang.String attr = null;
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
	 * @param branchNo the branchNo to set
	 */
	public void setBranchNo(java.lang.Long branchNo){
		this.branchNo = branchNo;
	}
	
	/**
	 * @return branchNo
	 */
	@Id
	@Column(name="BRANCH_NO")	 
	public java.lang.Long getBranchNo(){
		return branchNo;
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
		map.put(PK.name(),pk);
		map.put(BRANCH_NO.name(),branchNo);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(VERSION.name(),version);
		map.put(ATTR2.name(),attr2);
		map.put(ATTR.name(),attr);
		map.put(TEST.name(),test);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public GeneratedChild clone() {
		GeneratedChild clone = new GeneratedChild();
		clone.pk = pk;
		clone.branchNo = branchNo;
		clone.version = version;
		clone.attr2 = attr2;
		clone.attr = attr;
		clone.test = test;		
		return clone;
	}
}

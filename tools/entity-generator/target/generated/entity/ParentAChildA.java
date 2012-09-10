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
 * PARENT_A_CHILD_Aエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="PARENT_A_CHILD_A")
public class ParentAChildA extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** PARENT_TEST */
	public static final Metadata<ParentAChildA, java.lang.String> PARENT_TEST = new Metadata<ParentAChildA, java.lang.String>("PARENT_TEST");
	
	/** TEST */
	public static final Metadata<ParentAChildA, java.lang.String> TEST = new Metadata<ParentAChildA, java.lang.String>("TEST");
	
	/** PARENTAENTITY_TEST */
	public static final Metadata<ParentAChildA, java.lang.String> PARENTAENTITY_TEST = new Metadata<ParentAChildA, java.lang.String>("PARENTAENTITY_TEST");
		
	
	/**
	 * PARENT_TEST
	 */
	private java.lang.String parentTest = null;
	/**
	 * TEST
	 */
	private java.lang.String test = null;
	/**
	 * PARENTAENTITY_TEST
	 */
	private java.lang.String parentaentityTest = null;

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
	 * @param parentaentityTest the parentaentityTest to set
	 */
	public void setParentaentityTest(java.lang.String parentaentityTest){
		this.parentaentityTest = parentaentityTest;
	}
	
	/**
	 * @return parentaentityTest
	 */
	@Id
	@Column(name="PARENTAENTITY_TEST")	 
	public java.lang.String getParentaentityTest(){
		return parentaentityTest;
	}
	

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getVersioningValue()
	 */
	@Override
	public Pair<String> toVersioningValue() {	
		return null;		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getPrimaryKeys()
	 */
	@Override
	public Map<String, Object> toPrimaryKeys() {
		Map<String,Object> map = createMap();
		map.put(PARENT_TEST.name(),parentTest);
		map.put(TEST.name(),test);
		map.put(PARENTAENTITY_TEST.name(),parentaentityTest);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public ParentAChildA clone() {
		ParentAChildA clone = new ParentAChildA();
		clone.parentTest = parentTest;
		clone.test = test;
		clone.parentaentityTest = parentaentityTest;		
		return clone;
	}
}

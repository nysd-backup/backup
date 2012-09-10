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
 * TESTDエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTD")
public class Testd extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** TEST */
	public static final Metadata<Testd, java.lang.String> TEST = new Metadata<Testd, java.lang.String>("TEST");
		
	
	/**
	 * TEST
	 */
	private java.lang.String test = null;

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
		map.put(TEST.name(),test);
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
	public Testd clone() {
		Testd clone = new Testd();
		clone.test = test;		
		return clone;
	}
}

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
 * TESTCエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTC")
public class Testc extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** TEST */
	public static final Metadata<Testc, java.math.BigDecimal> TEST = new Metadata<Testc, java.math.BigDecimal>("TEST");
		
	
	/**
	 * TEST
	 */
	private java.math.BigDecimal test = null;

	/**
	 * @param test the test to set
	 */
	public void setTest(java.math.BigDecimal test){
		this.test = test;
	}
	
	/**
	 * @return test
	 */
	@Column(name="TEST")	 
	public java.math.BigDecimal getTest(){
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
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(TEST.name(),test);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public Testc clone() {
		Testc clone = new Testc();
		clone.test = test;		
		return clone;
	}
}

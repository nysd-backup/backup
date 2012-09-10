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
 * TYPE_TESTエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TYPE_TEST")
public class TypeTest extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** FOUR */
	public static final Metadata<TypeTest, java.io.InputStream> FOUR = new Metadata<TypeTest, java.io.InputStream>("FOUR");
	
	/** THREE */
	public static final Metadata<TypeTest, java.lang.Long> THREE = new Metadata<TypeTest, java.lang.Long>("THREE");
	
	/** TWO */
	public static final Metadata<TypeTest, java.math.BigDecimal> TWO = new Metadata<TypeTest, java.math.BigDecimal>("TWO");
	
	/** ONE */
	public static final Metadata<TypeTest, java.lang.Long> ONE = new Metadata<TypeTest, java.lang.Long>("ONE");
		
	
	/**
	 * FOUR
	 */
	private java.io.InputStream four = null;
	/**
	 * THREE
	 */
	private java.lang.Long three = null;
	/**
	 * TWO
	 */
	private java.math.BigDecimal two = null;
	/**
	 * ONE
	 */
	private java.lang.Long one = null;

	/**
	 * @param four the four to set
	 */
	public void setFour(java.io.InputStream four){
		this.four = four;
	}
	
	/**
	 * @return four
	 */
	@Column(name="FOUR")	 
	public java.io.InputStream getFour(){
		return four;
	}
	
	/**
	 * @param three the three to set
	 */
	public void setThree(java.lang.Long three){
		this.three = three;
	}
	
	/**
	 * @return three
	 */
	@Column(name="THREE")	 
	public java.lang.Long getThree(){
		return three;
	}
	
	/**
	 * @param two the two to set
	 */
	public void setTwo(java.math.BigDecimal two){
		this.two = two;
	}
	
	/**
	 * @return two
	 */
	@Column(name="TWO")	 
	public java.math.BigDecimal getTwo(){
		return two;
	}
	
	/**
	 * @param one the one to set
	 */
	public void setOne(java.lang.Long one){
		this.one = one;
	}
	
	/**
	 * @return one
	 */
	@Column(name="ONE")	 
	public java.lang.Long getOne(){
		return one;
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
		map.put(FOUR.name(),four);
		map.put(THREE.name(),three);
		map.put(TWO.name(),two);
		map.put(ONE.name(),one);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public TypeTest clone() {
		TypeTest clone = new TypeTest();
		clone.four = four;
		clone.three = three;
		clone.two = two;
		clone.one = one;		
		return clone;
	}
}

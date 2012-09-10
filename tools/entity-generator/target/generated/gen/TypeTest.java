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
	
	/** ONE */
	public static final Metadata<TypeTest, java.lang.Long> ONE = new Metadata<TypeTest, java.lang.Long>("ONE");
	
	/** THREE */
	public static final Metadata<TypeTest, java.lang.Long> THREE = new Metadata<TypeTest, java.lang.Long>("THREE");
	
	/** FOUR */
	public static final Metadata<TypeTest, java.io.InputStream> FOUR = new Metadata<TypeTest, java.io.InputStream>("FOUR");
	
	/** TWO */
	public static final Metadata<TypeTest, java.math.BigDecimal> TWO = new Metadata<TypeTest, java.math.BigDecimal>("TWO");
		
	
	/**
	 * ONE
	 */
	private java.lang.Long one = null;
	/**
	 * THREE
	 */
	private java.lang.Long three = null;
	/**
	 * FOUR
	 */
	private java.io.InputStream four = null;
	/**
	 * TWO
	 */
	private java.math.BigDecimal two = null;

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
		map.put(ONE.name(),one);
		map.put(THREE.name(),three);
		map.put(FOUR.name(),four);
		map.put(TWO.name(),two);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public TypeTest clone() {
		TypeTest clone = new TypeTest();
		clone.one = one;
		clone.three = three;
		clone.four = four;
		clone.two = two;		
		return clone;
	}
}

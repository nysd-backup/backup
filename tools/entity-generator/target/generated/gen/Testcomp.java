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
 * TESTCOMPエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTCOMP")
public class Testcomp extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** PK1 */
	public static final Metadata<Testcomp, java.lang.String> PK1 = new Metadata<Testcomp, java.lang.String>("PK1");
	
	/** PK2 */
	public static final Metadata<Testcomp, java.lang.String> PK2 = new Metadata<Testcomp, java.lang.String>("PK2");
	
	/** VALUE */
	public static final Metadata<Testcomp, java.lang.String> VALUE = new Metadata<Testcomp, java.lang.String>("VALUE");
		
	
	/**
	 * PK1
	 */
	private java.lang.String pk1 = null;
	/**
	 * PK2
	 */
	private java.lang.String pk2 = null;
	/**
	 * VALUE
	 */
	private java.lang.String value = null;

	/**
	 * @param pk1 the pk1 to set
	 */
	public void setPk1(java.lang.String pk1){
		this.pk1 = pk1;
	}
	
	/**
	 * @return pk1
	 */
	@Id
	@Column(name="PK1")	 
	public java.lang.String getPk1(){
		return pk1;
	}
	
	/**
	 * @param pk2 the pk2 to set
	 */
	public void setPk2(java.lang.String pk2){
		this.pk2 = pk2;
	}
	
	/**
	 * @return pk2
	 */
	@Id
	@Column(name="PK2")	 
	public java.lang.String getPk2(){
		return pk2;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(java.lang.String value){
		this.value = value;
	}
	
	/**
	 * @return value
	 */
	@Column(name="VALUE")	 
	public java.lang.String getValue(){
		return value;
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
		map.put(PK1.name(),pk1);
		map.put(PK2.name(),pk2);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(VALUE.name(),value);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public Testcomp clone() {
		Testcomp clone = new Testcomp();
		clone.pk1 = pk1;
		clone.pk2 = pk2;
		clone.value = value;		
		return clone;
	}
}

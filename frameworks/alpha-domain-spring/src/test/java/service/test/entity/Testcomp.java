/**
 * Use is subject to license terms.
 */
package service.test.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import alpha.sqlclient.orm.FastEntity;
import alpha.sqlclient.orm.Metadata;
import alpha.sqlclient.orm.Pair;




/**
 * TESTCOMPエンティティ
 *
 * @author Tool Auto Making
 */
@IdClass(TestcompPK.class)
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTCOMP")
public class Testcomp implements FastEntity , Cloneable{

	/** PK1 */
	public static final Metadata<Testcomp, java.lang.String> PK1 = new Metadata<Testcomp, java.lang.String>("pk1");
	
	/** PK2 */
	public static final Metadata<Testcomp, java.lang.String> PK2 = new Metadata<Testcomp, java.lang.String>("pk2");
	
	/** VALUE */
	public static final Metadata<Testcomp, java.lang.String> VALUE = new Metadata<Testcomp, java.lang.String>("value");
		
	
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
	 * @see alpha.sqlclient.elink.api.FastEntity#getVersioningValue()
	 */
	@Override
	public Pair<String> toVersioningValue() {	
		return null;		
	}
	
	/**
	 * @see alpha.sqlclient.elink.api.FastEntity#getPrimaryKeys()
	 */
	@Override
	public Map<String, Object> toPrimaryKeys() {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put(PK1.name(),pk1);
		map.put(PK2.name(),pk2);
		return map;
	}

	/**
	 * @see alpha.sqlclient.elink.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put(VALUE.name(),value);
		return map;
	}
	
	/**
	 * @see alpha.framework.core.base.AbstractBean#clone()
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
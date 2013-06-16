/**
 * Use is subject to license terms.
 */
package service.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.coder.alpha.query.criteria.Metadata;





/**
 * TESTCOMPエンティティ
 *
 * @author Tool Auto Making
 */
@IdClass(CompEntityPK.class)
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="TESTCOMP")
public class CompEntity{

	/** PK1 */
	public static final Metadata<CompEntity, java.lang.String> PK1 = new Metadata<CompEntity, java.lang.String>("PK1");
	
	/** PK2 */
	public static final Metadata<CompEntity, java.lang.String> PK2 = new Metadata<CompEntity, java.lang.String>("PK2");
	
	/** VALUE */
	public static final Metadata<CompEntity, java.lang.String> VALUE = new Metadata<CompEntity, java.lang.String>("VALUE");
		
	
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
	 * @see org.coder.alpha.framework.core.base.AbstractBean#clone()
	 */
	@Override
	public CompEntity clone() {
		CompEntity clone = new CompEntity();
		clone.pk1 = pk1;
		clone.pk2 = pk2;
		clone.value = value;		
		return clone;
	}
}

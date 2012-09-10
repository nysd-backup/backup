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
 * MR_FUNCエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="MR_FUNC")
public class MrFunc extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** FUNC_CD */
	public static final Metadata<MrFunc, java.lang.String> FUNC_CD = new Metadata<MrFunc, java.lang.String>("FUNC_CD");
	
	/** BASE_POINT_FUNC_CD */
	public static final Metadata<MrFunc, java.lang.String> BASE_POINT_FUNC_CD = new Metadata<MrFunc, java.lang.String>("BASE_POINT_FUNC_CD");
	
	/** SEQ */
	public static final Metadata<MrFunc, java.lang.Long> SEQ = new Metadata<MrFunc, java.lang.Long>("SEQ");
		
	
	/**
	 * FUNC_CD
	 */
	private java.lang.String funcCd = null;
	/**
	 * BASE_POINT_FUNC_CD
	 */
	private java.lang.String basePointFuncCd = null;
	/**
	 * SEQ
	 */
	private java.lang.Long seq = null;

	/**
	 * @param funcCd the funcCd to set
	 */
	public void setFuncCd(java.lang.String funcCd){
		this.funcCd = funcCd;
	}
	
	/**
	 * @return funcCd
	 */
	@Id
	@Column(name="FUNC_CD")	 
	public java.lang.String getFuncCd(){
		return funcCd;
	}
	
	/**
	 * @param basePointFuncCd the basePointFuncCd to set
	 */
	public void setBasePointFuncCd(java.lang.String basePointFuncCd){
		this.basePointFuncCd = basePointFuncCd;
	}
	
	/**
	 * @return basePointFuncCd
	 */
	@Id
	@Column(name="BASE_POINT_FUNC_CD")	 
	public java.lang.String getBasePointFuncCd(){
		return basePointFuncCd;
	}
	
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(java.lang.Long seq){
		this.seq = seq;
	}
	
	/**
	 * @return seq
	 */
	@Column(name="SEQ")	 
	public java.lang.Long getSeq(){
		return seq;
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
		map.put(FUNC_CD.name(),funcCd);
		map.put(BASE_POINT_FUNC_CD.name(),basePointFuncCd);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(SEQ.name(),seq);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public MrFunc clone() {
		MrFunc clone = new MrFunc();
		clone.funcCd = funcCd;
		clone.basePointFuncCd = basePointFuncCd;
		clone.seq = seq;		
		return clone;
	}
}

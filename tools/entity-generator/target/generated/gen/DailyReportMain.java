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
 * DAILY_REPORT_MAINエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="DAILY_REPORT_MAIN")
public class DailyReportMain extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** EIGY_KEJO_YM */
	public static final Metadata<DailyReportMain, java.lang.String> EIGY_KEJO_YM = new Metadata<DailyReportMain, java.lang.String>("EIGY_KEJO_YM");
	
	/** ED_NO */
	public static final Metadata<DailyReportMain, java.lang.Long> ED_NO = new Metadata<DailyReportMain, java.lang.Long>("ED_NO");
	
	/** ACCUM_TRN_NUMBER */
	public static final Metadata<DailyReportMain, java.lang.Long> ACCUM_TRN_NUMBER = new Metadata<DailyReportMain, java.lang.Long>("ACCUM_TRN_NUMBER");
	
	/** OL_UPD_BUSHO_NM */
	public static final Metadata<DailyReportMain, java.lang.String> OL_UPD_BUSHO_NM = new Metadata<DailyReportMain, java.lang.String>("OL_UPD_BUSHO_NM");
	
	/** CRE_SIMEI_CD */
	public static final Metadata<DailyReportMain, java.lang.String> CRE_SIMEI_CD = new Metadata<DailyReportMain, java.lang.String>("CRE_SIMEI_CD");
	
	/** DEL_KBN */
	public static final Metadata<DailyReportMain, java.lang.String> DEL_KBN = new Metadata<DailyReportMain, java.lang.String>("DEL_KBN");
	
	/** VALUE */
	public static final Metadata<DailyReportMain, java.lang.String> VALUE = new Metadata<DailyReportMain, java.lang.String>("VALUE");
	
	/** DAT_ENT_MT_KBN */
	public static final Metadata<DailyReportMain, java.lang.String> DAT_ENT_MT_KBN = new Metadata<DailyReportMain, java.lang.String>("DAT_ENT_MT_KBN");
	
	/** TRN_FIX_STS_KBN */
	public static final Metadata<DailyReportMain, java.lang.String> TRN_FIX_STS_KBN = new Metadata<DailyReportMain, java.lang.String>("TRN_FIX_STS_KBN");
	
	/** OL_UPD_SIMEI_CD */
	public static final Metadata<DailyReportMain, java.lang.String> OL_UPD_SIMEI_CD = new Metadata<DailyReportMain, java.lang.String>("OL_UPD_SIMEI_CD");
	
	/** CRE_SIMEI */
	public static final Metadata<DailyReportMain, java.lang.String> CRE_SIMEI = new Metadata<DailyReportMain, java.lang.String>("CRE_SIMEI");
	
	/** CRE_DATE */
	public static final Metadata<DailyReportMain, java.util.Date> CRE_DATE = new Metadata<DailyReportMain, java.util.Date>("CRE_DATE");
	
	/** OL_UPD_PRG_ID */
	public static final Metadata<DailyReportMain, java.lang.String> OL_UPD_PRG_ID = new Metadata<DailyReportMain, java.lang.String>("OL_UPD_PRG_ID");
	
	/** UPD_TERM_ID */
	public static final Metadata<DailyReportMain, java.lang.String> UPD_TERM_ID = new Metadata<DailyReportMain, java.lang.String>("UPD_TERM_ID");
	
	/** OL_UPD_BUSHO_CD */
	public static final Metadata<DailyReportMain, java.lang.String> OL_UPD_BUSHO_CD = new Metadata<DailyReportMain, java.lang.String>("OL_UPD_BUSHO_CD");
	
	/** YMD */
	public static final Metadata<DailyReportMain, java.util.Date> YMD = new Metadata<DailyReportMain, java.util.Date>("YMD");
	
	/** PROC_STS_KBN2 */
	public static final Metadata<DailyReportMain, java.lang.String> PROC_STS_KBN2 = new Metadata<DailyReportMain, java.lang.String>("PROC_STS_KBN2");
	
	/** OL_UPD_SIMEI */
	public static final Metadata<DailyReportMain, java.lang.String> OL_UPD_SIMEI = new Metadata<DailyReportMain, java.lang.String>("OL_UPD_SIMEI");
	
	/** BAT_UPD_DATE */
	public static final Metadata<DailyReportMain, java.util.Date> BAT_UPD_DATE = new Metadata<DailyReportMain, java.util.Date>("BAT_UPD_DATE");
	
	/** OL_UPD_DATE */
	public static final Metadata<DailyReportMain, java.util.Date> OL_UPD_DATE = new Metadata<DailyReportMain, java.util.Date>("OL_UPD_DATE");
	
	/** PROC_STS_KBN */
	public static final Metadata<DailyReportMain, java.lang.String> PROC_STS_KBN = new Metadata<DailyReportMain, java.lang.String>("PROC_STS_KBN");
	
	/** CRE_BUSHO_CD */
	public static final Metadata<DailyReportMain, java.lang.String> CRE_BUSHO_CD = new Metadata<DailyReportMain, java.lang.String>("CRE_BUSHO_CD");
	
	/** BAT_UPD_PRG_ID */
	public static final Metadata<DailyReportMain, java.lang.String> BAT_UPD_PRG_ID = new Metadata<DailyReportMain, java.lang.String>("BAT_UPD_PRG_ID");
	
	/** LOCK_SEQNO */
	public static final Metadata<DailyReportMain, java.lang.Long> LOCK_SEQNO = new Metadata<DailyReportMain, java.lang.Long>("LOCK_SEQNO");
	
	/** CRE_BUSHO_NM */
	public static final Metadata<DailyReportMain, java.lang.String> CRE_BUSHO_NM = new Metadata<DailyReportMain, java.lang.String>("CRE_BUSHO_NM");
	
	/** CRE_PRG_ID */
	public static final Metadata<DailyReportMain, java.lang.String> CRE_PRG_ID = new Metadata<DailyReportMain, java.lang.String>("CRE_PRG_ID");
	
	/** SHR_KBN */
	public static final Metadata<DailyReportMain, java.lang.String> SHR_KBN = new Metadata<DailyReportMain, java.lang.String>("SHR_KBN");
	
	/** MONEY */
	public static final Metadata<DailyReportMain, java.lang.Long> MONEY = new Metadata<DailyReportMain, java.lang.Long>("MONEY");
		
	
	/**
	 * EIGY_KEJO_YM
	 */
	private java.lang.String eigyKejoYm = null;
	/**
	 * ED_NO
	 */
	private java.lang.Long edNo = null;
	/**
	 * ACCUM_TRN_NUMBER
	 */
	private java.lang.Long accumTrnNumber = null;
	/**
	 * OL_UPD_BUSHO_NM
	 */
	private java.lang.String olUpdBushoNm = null;
	/**
	 * CRE_SIMEI_CD
	 */
	private java.lang.String creSimeiCd = null;
	/**
	 * DEL_KBN
	 */
	private java.lang.String delKbn = null;
	/**
	 * VALUE
	 */
	private java.lang.String value = null;
	/**
	 * DAT_ENT_MT_KBN
	 */
	private java.lang.String datEntMtKbn = null;
	/**
	 * TRN_FIX_STS_KBN
	 */
	private java.lang.String trnFixStsKbn = null;
	/**
	 * OL_UPD_SIMEI_CD
	 */
	private java.lang.String olUpdSimeiCd = null;
	/**
	 * CRE_SIMEI
	 */
	private java.lang.String creSimei = null;
	/**
	 * CRE_DATE
	 */
	private java.util.Date creDate = null;
	/**
	 * OL_UPD_PRG_ID
	 */
	private java.lang.String olUpdPrgId = null;
	/**
	 * UPD_TERM_ID
	 */
	private java.lang.String updTermId = null;
	/**
	 * OL_UPD_BUSHO_CD
	 */
	private java.lang.String olUpdBushoCd = null;
	/**
	 * YMD
	 */
	private java.util.Date ymd = null;
	/**
	 * PROC_STS_KBN2
	 */
	private java.lang.String procStsKbn2 = null;
	/**
	 * OL_UPD_SIMEI
	 */
	private java.lang.String olUpdSimei = null;
	/**
	 * BAT_UPD_DATE
	 */
	private java.util.Date batUpdDate = null;
	/**
	 * OL_UPD_DATE
	 */
	private java.util.Date olUpdDate = null;
	/**
	 * PROC_STS_KBN
	 */
	private java.lang.String procStsKbn = null;
	/**
	 * CRE_BUSHO_CD
	 */
	private java.lang.String creBushoCd = null;
	/**
	 * BAT_UPD_PRG_ID
	 */
	private java.lang.String batUpdPrgId = null;
	/**
	 * LOCK_SEQNO
	 */
	private java.lang.Long lockSeqno = null;
	/**
	 * CRE_BUSHO_NM
	 */
	private java.lang.String creBushoNm = null;
	/**
	 * CRE_PRG_ID
	 */
	private java.lang.String crePrgId = null;
	/**
	 * SHR_KBN
	 */
	private java.lang.String shrKbn = null;
	/**
	 * MONEY
	 */
	private java.lang.Long money = null;

	/**
	 * @param eigyKejoYm the eigyKejoYm to set
	 */
	public void setEigyKejoYm(java.lang.String eigyKejoYm){
		this.eigyKejoYm = eigyKejoYm;
	}
	
	/**
	 * @return eigyKejoYm
	 */
	@Id
	@Column(name="EIGY_KEJO_YM")	 
	public java.lang.String getEigyKejoYm(){
		return eigyKejoYm;
	}
	
	/**
	 * @param edNo the edNo to set
	 */
	public void setEdNo(java.lang.Long edNo){
		this.edNo = edNo;
	}
	
	/**
	 * @return edNo
	 */
	@Id
	@Column(name="ED_NO")	 
	public java.lang.Long getEdNo(){
		return edNo;
	}
	
	/**
	 * @param accumTrnNumber the accumTrnNumber to set
	 */
	public void setAccumTrnNumber(java.lang.Long accumTrnNumber){
		this.accumTrnNumber = accumTrnNumber;
	}
	
	/**
	 * @return accumTrnNumber
	 */
	@Id
	@Column(name="ACCUM_TRN_NUMBER")	 
	public java.lang.Long getAccumTrnNumber(){
		return accumTrnNumber;
	}
	
	/**
	 * @param olUpdBushoNm the olUpdBushoNm to set
	 */
	public void setOlUpdBushoNm(java.lang.String olUpdBushoNm){
		this.olUpdBushoNm = olUpdBushoNm;
	}
	
	/**
	 * @return olUpdBushoNm
	 */
	@Column(name="OL_UPD_BUSHO_NM")	 
	public java.lang.String getOlUpdBushoNm(){
		return olUpdBushoNm;
	}
	
	/**
	 * @param creSimeiCd the creSimeiCd to set
	 */
	public void setCreSimeiCd(java.lang.String creSimeiCd){
		this.creSimeiCd = creSimeiCd;
	}
	
	/**
	 * @return creSimeiCd
	 */
	@Column(name="CRE_SIMEI_CD")	 
	public java.lang.String getCreSimeiCd(){
		return creSimeiCd;
	}
	
	/**
	 * @param delKbn the delKbn to set
	 */
	public void setDelKbn(java.lang.String delKbn){
		this.delKbn = delKbn;
	}
	
	/**
	 * @return delKbn
	 */
	@Column(name="DEL_KBN")	 
	public java.lang.String getDelKbn(){
		return delKbn;
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
	 * @param datEntMtKbn the datEntMtKbn to set
	 */
	public void setDatEntMtKbn(java.lang.String datEntMtKbn){
		this.datEntMtKbn = datEntMtKbn;
	}
	
	/**
	 * @return datEntMtKbn
	 */
	@Column(name="DAT_ENT_MT_KBN")	 
	public java.lang.String getDatEntMtKbn(){
		return datEntMtKbn;
	}
	
	/**
	 * @param trnFixStsKbn the trnFixStsKbn to set
	 */
	public void setTrnFixStsKbn(java.lang.String trnFixStsKbn){
		this.trnFixStsKbn = trnFixStsKbn;
	}
	
	/**
	 * @return trnFixStsKbn
	 */
	@Column(name="TRN_FIX_STS_KBN")	 
	public java.lang.String getTrnFixStsKbn(){
		return trnFixStsKbn;
	}
	
	/**
	 * @param olUpdSimeiCd the olUpdSimeiCd to set
	 */
	public void setOlUpdSimeiCd(java.lang.String olUpdSimeiCd){
		this.olUpdSimeiCd = olUpdSimeiCd;
	}
	
	/**
	 * @return olUpdSimeiCd
	 */
	@Column(name="OL_UPD_SIMEI_CD")	 
	public java.lang.String getOlUpdSimeiCd(){
		return olUpdSimeiCd;
	}
	
	/**
	 * @param creSimei the creSimei to set
	 */
	public void setCreSimei(java.lang.String creSimei){
		this.creSimei = creSimei;
	}
	
	/**
	 * @return creSimei
	 */
	@Column(name="CRE_SIMEI")	 
	public java.lang.String getCreSimei(){
		return creSimei;
	}
	
	/**
	 * @param creDate the creDate to set
	 */
	public void setCreDate(java.util.Date creDate){
		this.creDate = creDate;
	}
	
	/**
	 * @return creDate
	 */
	@Column(name="CRE_DATE")	 
	public java.util.Date getCreDate(){
		return creDate;
	}
	
	/**
	 * @param olUpdPrgId the olUpdPrgId to set
	 */
	public void setOlUpdPrgId(java.lang.String olUpdPrgId){
		this.olUpdPrgId = olUpdPrgId;
	}
	
	/**
	 * @return olUpdPrgId
	 */
	@Column(name="OL_UPD_PRG_ID")	 
	public java.lang.String getOlUpdPrgId(){
		return olUpdPrgId;
	}
	
	/**
	 * @param updTermId the updTermId to set
	 */
	public void setUpdTermId(java.lang.String updTermId){
		this.updTermId = updTermId;
	}
	
	/**
	 * @return updTermId
	 */
	@Column(name="UPD_TERM_ID")	 
	public java.lang.String getUpdTermId(){
		return updTermId;
	}
	
	/**
	 * @param olUpdBushoCd the olUpdBushoCd to set
	 */
	public void setOlUpdBushoCd(java.lang.String olUpdBushoCd){
		this.olUpdBushoCd = olUpdBushoCd;
	}
	
	/**
	 * @return olUpdBushoCd
	 */
	@Column(name="OL_UPD_BUSHO_CD")	 
	public java.lang.String getOlUpdBushoCd(){
		return olUpdBushoCd;
	}
	
	/**
	 * @param ymd the ymd to set
	 */
	public void setYmd(java.util.Date ymd){
		this.ymd = ymd;
	}
	
	/**
	 * @return ymd
	 */
	@Column(name="YMD")	 
	public java.util.Date getYmd(){
		return ymd;
	}
	
	/**
	 * @param procStsKbn2 the procStsKbn2 to set
	 */
	public void setProcStsKbn2(java.lang.String procStsKbn2){
		this.procStsKbn2 = procStsKbn2;
	}
	
	/**
	 * @return procStsKbn2
	 */
	@Column(name="PROC_STS_KBN2")	 
	public java.lang.String getProcStsKbn2(){
		return procStsKbn2;
	}
	
	/**
	 * @param olUpdSimei the olUpdSimei to set
	 */
	public void setOlUpdSimei(java.lang.String olUpdSimei){
		this.olUpdSimei = olUpdSimei;
	}
	
	/**
	 * @return olUpdSimei
	 */
	@Column(name="OL_UPD_SIMEI")	 
	public java.lang.String getOlUpdSimei(){
		return olUpdSimei;
	}
	
	/**
	 * @param batUpdDate the batUpdDate to set
	 */
	public void setBatUpdDate(java.util.Date batUpdDate){
		this.batUpdDate = batUpdDate;
	}
	
	/**
	 * @return batUpdDate
	 */
	@Column(name="BAT_UPD_DATE")	 
	public java.util.Date getBatUpdDate(){
		return batUpdDate;
	}
	
	/**
	 * @param olUpdDate the olUpdDate to set
	 */
	public void setOlUpdDate(java.util.Date olUpdDate){
		this.olUpdDate = olUpdDate;
	}
	
	/**
	 * @return olUpdDate
	 */
	@Column(name="OL_UPD_DATE")	 
	public java.util.Date getOlUpdDate(){
		return olUpdDate;
	}
	
	/**
	 * @param procStsKbn the procStsKbn to set
	 */
	public void setProcStsKbn(java.lang.String procStsKbn){
		this.procStsKbn = procStsKbn;
	}
	
	/**
	 * @return procStsKbn
	 */
	@Column(name="PROC_STS_KBN")	 
	public java.lang.String getProcStsKbn(){
		return procStsKbn;
	}
	
	/**
	 * @param creBushoCd the creBushoCd to set
	 */
	public void setCreBushoCd(java.lang.String creBushoCd){
		this.creBushoCd = creBushoCd;
	}
	
	/**
	 * @return creBushoCd
	 */
	@Column(name="CRE_BUSHO_CD")	 
	public java.lang.String getCreBushoCd(){
		return creBushoCd;
	}
	
	/**
	 * @param batUpdPrgId the batUpdPrgId to set
	 */
	public void setBatUpdPrgId(java.lang.String batUpdPrgId){
		this.batUpdPrgId = batUpdPrgId;
	}
	
	/**
	 * @return batUpdPrgId
	 */
	@Column(name="BAT_UPD_PRG_ID")	 
	public java.lang.String getBatUpdPrgId(){
		return batUpdPrgId;
	}
	
	/**
	 * @param lockSeqno the lockSeqno to set
	 */
	public void setLockSeqno(java.lang.Long lockSeqno){
		this.lockSeqno = lockSeqno;
	}
	
	/**
	 * @return lockSeqno
	 */
	@Column(name="LOCK_SEQNO")	 
	public java.lang.Long getLockSeqno(){
		return lockSeqno;
	}
	
	/**
	 * @param creBushoNm the creBushoNm to set
	 */
	public void setCreBushoNm(java.lang.String creBushoNm){
		this.creBushoNm = creBushoNm;
	}
	
	/**
	 * @return creBushoNm
	 */
	@Column(name="CRE_BUSHO_NM")	 
	public java.lang.String getCreBushoNm(){
		return creBushoNm;
	}
	
	/**
	 * @param crePrgId the crePrgId to set
	 */
	public void setCrePrgId(java.lang.String crePrgId){
		this.crePrgId = crePrgId;
	}
	
	/**
	 * @return crePrgId
	 */
	@Column(name="CRE_PRG_ID")	 
	public java.lang.String getCrePrgId(){
		return crePrgId;
	}
	
	/**
	 * @param shrKbn the shrKbn to set
	 */
	public void setShrKbn(java.lang.String shrKbn){
		this.shrKbn = shrKbn;
	}
	
	/**
	 * @return shrKbn
	 */
	@Column(name="SHR_KBN")	 
	public java.lang.String getShrKbn(){
		return shrKbn;
	}
	
	/**
	 * @param money the money to set
	 */
	public void setMoney(java.lang.Long money){
		this.money = money;
	}
	
	/**
	 * @return money
	 */
	@Column(name="MONEY")	 
	public java.lang.Long getMoney(){
		return money;
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
		map.put(EIGY_KEJO_YM.name(),eigyKejoYm);
		map.put(ED_NO.name(),edNo);
		map.put(ACCUM_TRN_NUMBER.name(),accumTrnNumber);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(OL_UPD_BUSHO_NM.name(),olUpdBushoNm);
		map.put(CRE_SIMEI_CD.name(),creSimeiCd);
		map.put(DEL_KBN.name(),delKbn);
		map.put(VALUE.name(),value);
		map.put(DAT_ENT_MT_KBN.name(),datEntMtKbn);
		map.put(TRN_FIX_STS_KBN.name(),trnFixStsKbn);
		map.put(OL_UPD_SIMEI_CD.name(),olUpdSimeiCd);
		map.put(CRE_SIMEI.name(),creSimei);
		map.put(CRE_DATE.name(),creDate);
		map.put(OL_UPD_PRG_ID.name(),olUpdPrgId);
		map.put(UPD_TERM_ID.name(),updTermId);
		map.put(OL_UPD_BUSHO_CD.name(),olUpdBushoCd);
		map.put(YMD.name(),ymd);
		map.put(PROC_STS_KBN2.name(),procStsKbn2);
		map.put(OL_UPD_SIMEI.name(),olUpdSimei);
		map.put(BAT_UPD_DATE.name(),batUpdDate);
		map.put(OL_UPD_DATE.name(),olUpdDate);
		map.put(PROC_STS_KBN.name(),procStsKbn);
		map.put(CRE_BUSHO_CD.name(),creBushoCd);
		map.put(BAT_UPD_PRG_ID.name(),batUpdPrgId);
		map.put(LOCK_SEQNO.name(),lockSeqno);
		map.put(CRE_BUSHO_NM.name(),creBushoNm);
		map.put(CRE_PRG_ID.name(),crePrgId);
		map.put(SHR_KBN.name(),shrKbn);
		map.put(MONEY.name(),money);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public DailyReportMain clone() {
		DailyReportMain clone = new DailyReportMain();
		clone.eigyKejoYm = eigyKejoYm;
		clone.edNo = edNo;
		clone.accumTrnNumber = accumTrnNumber;
		clone.olUpdBushoNm = olUpdBushoNm;
		clone.creSimeiCd = creSimeiCd;
		clone.delKbn = delKbn;
		clone.value = value;
		clone.datEntMtKbn = datEntMtKbn;
		clone.trnFixStsKbn = trnFixStsKbn;
		clone.olUpdSimeiCd = olUpdSimeiCd;
		clone.creSimei = creSimei;
		clone.creDate = creDate;
		clone.olUpdPrgId = olUpdPrgId;
		clone.updTermId = updTermId;
		clone.olUpdBushoCd = olUpdBushoCd;
		clone.ymd = ymd;
		clone.procStsKbn2 = procStsKbn2;
		clone.olUpdSimei = olUpdSimei;
		clone.batUpdDate = batUpdDate;
		clone.olUpdDate = olUpdDate;
		clone.procStsKbn = procStsKbn;
		clone.creBushoCd = creBushoCd;
		clone.batUpdPrgId = batUpdPrgId;
		clone.lockSeqno = lockSeqno;
		clone.creBushoNm = creBushoNm;
		clone.crePrgId = crePrgId;
		clone.shrKbn = shrKbn;
		clone.money = money;		
		return clone;
	}
}

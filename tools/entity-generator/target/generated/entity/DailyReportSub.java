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
 * DAILY_REPORT_SUBエンティティ
 *
 * @author Tool Auto Making
 */
@Generated("kosmos.tool.entity-generator")
@Entity
@Table(name="DAILY_REPORT_SUB")
public class DailyReportSub extends AbstractEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** ED_NO */
	public static final Metadata<DailyReportSub, java.lang.Long> ED_NO = new Metadata<DailyReportSub, java.lang.Long>("ED_NO");
	
	/** EIGY_KEJO_YM */
	public static final Metadata<DailyReportSub, java.lang.String> EIGY_KEJO_YM = new Metadata<DailyReportSub, java.lang.String>("EIGY_KEJO_YM");
	
	/** ACCUM_TRN_NUMBER */
	public static final Metadata<DailyReportSub, java.lang.Long> ACCUM_TRN_NUMBER = new Metadata<DailyReportSub, java.lang.Long>("ACCUM_TRN_NUMBER");
	
	/** ROW_NO */
	public static final Metadata<DailyReportSub, java.lang.Long> ROW_NO = new Metadata<DailyReportSub, java.lang.Long>("ROW_NO");
	
	/** YMD */
	public static final Metadata<DailyReportSub, java.util.Date> YMD = new Metadata<DailyReportSub, java.util.Date>("YMD");
	
	/** CRE_PRG_ID */
	public static final Metadata<DailyReportSub, java.lang.String> CRE_PRG_ID = new Metadata<DailyReportSub, java.lang.String>("CRE_PRG_ID");
	
	/** CRE_BUSHO_CD */
	public static final Metadata<DailyReportSub, java.lang.String> CRE_BUSHO_CD = new Metadata<DailyReportSub, java.lang.String>("CRE_BUSHO_CD");
	
	/** TRN_FIX_STS_KBN */
	public static final Metadata<DailyReportSub, java.lang.String> TRN_FIX_STS_KBN = new Metadata<DailyReportSub, java.lang.String>("TRN_FIX_STS_KBN");
	
	/** SHR_KBN */
	public static final Metadata<DailyReportSub, java.lang.String> SHR_KBN = new Metadata<DailyReportSub, java.lang.String>("SHR_KBN");
	
	/** PARENT_ACCUM_TRN_NUMBER */
	public static final Metadata<DailyReportSub, java.lang.Long> PARENT_ACCUM_TRN_NUMBER = new Metadata<DailyReportSub, java.lang.Long>("PARENT_ACCUM_TRN_NUMBER");
	
	/** CRE_SIMEI */
	public static final Metadata<DailyReportSub, java.lang.String> CRE_SIMEI = new Metadata<DailyReportSub, java.lang.String>("CRE_SIMEI");
	
	/** DAT_ENT_MT_KBN */
	public static final Metadata<DailyReportSub, java.lang.String> DAT_ENT_MT_KBN = new Metadata<DailyReportSub, java.lang.String>("DAT_ENT_MT_KBN");
	
	/** PROC_STS_KBN2 */
	public static final Metadata<DailyReportSub, java.lang.String> PROC_STS_KBN2 = new Metadata<DailyReportSub, java.lang.String>("PROC_STS_KBN2");
	
	/** DEL_KBN */
	public static final Metadata<DailyReportSub, java.lang.String> DEL_KBN = new Metadata<DailyReportSub, java.lang.String>("DEL_KBN");
	
	/** UPD_TERM_ID */
	public static final Metadata<DailyReportSub, java.lang.String> UPD_TERM_ID = new Metadata<DailyReportSub, java.lang.String>("UPD_TERM_ID");
	
	/** CRE_DATE */
	public static final Metadata<DailyReportSub, java.util.Date> CRE_DATE = new Metadata<DailyReportSub, java.util.Date>("CRE_DATE");
	
	/** PARENT_ED_NO */
	public static final Metadata<DailyReportSub, java.lang.Long> PARENT_ED_NO = new Metadata<DailyReportSub, java.lang.Long>("PARENT_ED_NO");
	
	/** OL_UPD_SIMEI */
	public static final Metadata<DailyReportSub, java.lang.String> OL_UPD_SIMEI = new Metadata<DailyReportSub, java.lang.String>("OL_UPD_SIMEI");
	
	/** OL_UPD_BUSHO_CD */
	public static final Metadata<DailyReportSub, java.lang.String> OL_UPD_BUSHO_CD = new Metadata<DailyReportSub, java.lang.String>("OL_UPD_BUSHO_CD");
	
	/** MONEY */
	public static final Metadata<DailyReportSub, java.lang.Long> MONEY = new Metadata<DailyReportSub, java.lang.Long>("MONEY");
	
	/** BAT_UPD_PRG_ID */
	public static final Metadata<DailyReportSub, java.lang.String> BAT_UPD_PRG_ID = new Metadata<DailyReportSub, java.lang.String>("BAT_UPD_PRG_ID");
	
	/** CRE_SIMEI_CD */
	public static final Metadata<DailyReportSub, java.lang.String> CRE_SIMEI_CD = new Metadata<DailyReportSub, java.lang.String>("CRE_SIMEI_CD");
	
	/** OL_UPD_PRG_ID */
	public static final Metadata<DailyReportSub, java.lang.String> OL_UPD_PRG_ID = new Metadata<DailyReportSub, java.lang.String>("OL_UPD_PRG_ID");
	
	/** BAT_UPD_DATE */
	public static final Metadata<DailyReportSub, java.util.Date> BAT_UPD_DATE = new Metadata<DailyReportSub, java.util.Date>("BAT_UPD_DATE");
	
	/** PROC_STS_KBN */
	public static final Metadata<DailyReportSub, java.lang.String> PROC_STS_KBN = new Metadata<DailyReportSub, java.lang.String>("PROC_STS_KBN");
	
	/** OL_UPD_BUSHO_NM */
	public static final Metadata<DailyReportSub, java.lang.String> OL_UPD_BUSHO_NM = new Metadata<DailyReportSub, java.lang.String>("OL_UPD_BUSHO_NM");
	
	/** LOCK_SEQNO */
	public static final Metadata<DailyReportSub, java.lang.Long> LOCK_SEQNO = new Metadata<DailyReportSub, java.lang.Long>("LOCK_SEQNO");
	
	/** CRE_BUSHO_NM */
	public static final Metadata<DailyReportSub, java.lang.String> CRE_BUSHO_NM = new Metadata<DailyReportSub, java.lang.String>("CRE_BUSHO_NM");
	
	/** VALUE */
	public static final Metadata<DailyReportSub, java.lang.String> VALUE = new Metadata<DailyReportSub, java.lang.String>("VALUE");
	
	/** OL_UPD_DATE */
	public static final Metadata<DailyReportSub, java.util.Date> OL_UPD_DATE = new Metadata<DailyReportSub, java.util.Date>("OL_UPD_DATE");
	
	/** OL_UPD_SIMEI_CD */
	public static final Metadata<DailyReportSub, java.lang.String> OL_UPD_SIMEI_CD = new Metadata<DailyReportSub, java.lang.String>("OL_UPD_SIMEI_CD");
		
	
	/**
	 * ED_NO
	 */
	private java.lang.Long edNo = null;
	/**
	 * EIGY_KEJO_YM
	 */
	private java.lang.String eigyKejoYm = null;
	/**
	 * ACCUM_TRN_NUMBER
	 */
	private java.lang.Long accumTrnNumber = null;
	/**
	 * ROW_NO
	 */
	private java.lang.Long rowNo = null;
	/**
	 * YMD
	 */
	private java.util.Date ymd = null;
	/**
	 * CRE_PRG_ID
	 */
	private java.lang.String crePrgId = null;
	/**
	 * CRE_BUSHO_CD
	 */
	private java.lang.String creBushoCd = null;
	/**
	 * TRN_FIX_STS_KBN
	 */
	private java.lang.String trnFixStsKbn = null;
	/**
	 * SHR_KBN
	 */
	private java.lang.String shrKbn = null;
	/**
	 * PARENT_ACCUM_TRN_NUMBER
	 */
	private java.lang.Long parentAccumTrnNumber = null;
	/**
	 * CRE_SIMEI
	 */
	private java.lang.String creSimei = null;
	/**
	 * DAT_ENT_MT_KBN
	 */
	private java.lang.String datEntMtKbn = null;
	/**
	 * PROC_STS_KBN2
	 */
	private java.lang.String procStsKbn2 = null;
	/**
	 * DEL_KBN
	 */
	private java.lang.String delKbn = null;
	/**
	 * UPD_TERM_ID
	 */
	private java.lang.String updTermId = null;
	/**
	 * CRE_DATE
	 */
	private java.util.Date creDate = null;
	/**
	 * PARENT_ED_NO
	 */
	private java.lang.Long parentEdNo = null;
	/**
	 * OL_UPD_SIMEI
	 */
	private java.lang.String olUpdSimei = null;
	/**
	 * OL_UPD_BUSHO_CD
	 */
	private java.lang.String olUpdBushoCd = null;
	/**
	 * MONEY
	 */
	private java.lang.Long money = null;
	/**
	 * BAT_UPD_PRG_ID
	 */
	private java.lang.String batUpdPrgId = null;
	/**
	 * CRE_SIMEI_CD
	 */
	private java.lang.String creSimeiCd = null;
	/**
	 * OL_UPD_PRG_ID
	 */
	private java.lang.String olUpdPrgId = null;
	/**
	 * BAT_UPD_DATE
	 */
	private java.util.Date batUpdDate = null;
	/**
	 * PROC_STS_KBN
	 */
	private java.lang.String procStsKbn = null;
	/**
	 * OL_UPD_BUSHO_NM
	 */
	private java.lang.String olUpdBushoNm = null;
	/**
	 * LOCK_SEQNO
	 */
	private java.lang.Long lockSeqno = null;
	/**
	 * CRE_BUSHO_NM
	 */
	private java.lang.String creBushoNm = null;
	/**
	 * VALUE
	 */
	private java.lang.String value = null;
	/**
	 * OL_UPD_DATE
	 */
	private java.util.Date olUpdDate = null;
	/**
	 * OL_UPD_SIMEI_CD
	 */
	private java.lang.String olUpdSimeiCd = null;

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
	 * @param rowNo the rowNo to set
	 */
	public void setRowNo(java.lang.Long rowNo){
		this.rowNo = rowNo;
	}
	
	/**
	 * @return rowNo
	 */
	@Id
	@Column(name="ROW_NO")	 
	public java.lang.Long getRowNo(){
		return rowNo;
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
	 * @param parentAccumTrnNumber the parentAccumTrnNumber to set
	 */
	public void setParentAccumTrnNumber(java.lang.Long parentAccumTrnNumber){
		this.parentAccumTrnNumber = parentAccumTrnNumber;
	}
	
	/**
	 * @return parentAccumTrnNumber
	 */
	@Column(name="PARENT_ACCUM_TRN_NUMBER")	 
	public java.lang.Long getParentAccumTrnNumber(){
		return parentAccumTrnNumber;
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
	 * @param parentEdNo the parentEdNo to set
	 */
	public void setParentEdNo(java.lang.Long parentEdNo){
		this.parentEdNo = parentEdNo;
	}
	
	/**
	 * @return parentEdNo
	 */
	@Column(name="PARENT_ED_NO")	 
	public java.lang.Long getParentEdNo(){
		return parentEdNo;
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
		map.put(ED_NO.name(),edNo);
		map.put(EIGY_KEJO_YM.name(),eigyKejoYm);
		map.put(ACCUM_TRN_NUMBER.name(),accumTrnNumber);
		map.put(ROW_NO.name(),rowNo);
		return map;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.FastEntity#getAttributes()
	 */
	@Override
	public Map<String, Object> toAttributes() {
		Map<String,Object> map = createMap();
		map.put(YMD.name(),ymd);
		map.put(CRE_PRG_ID.name(),crePrgId);
		map.put(CRE_BUSHO_CD.name(),creBushoCd);
		map.put(TRN_FIX_STS_KBN.name(),trnFixStsKbn);
		map.put(SHR_KBN.name(),shrKbn);
		map.put(PARENT_ACCUM_TRN_NUMBER.name(),parentAccumTrnNumber);
		map.put(CRE_SIMEI.name(),creSimei);
		map.put(DAT_ENT_MT_KBN.name(),datEntMtKbn);
		map.put(PROC_STS_KBN2.name(),procStsKbn2);
		map.put(DEL_KBN.name(),delKbn);
		map.put(UPD_TERM_ID.name(),updTermId);
		map.put(CRE_DATE.name(),creDate);
		map.put(PARENT_ED_NO.name(),parentEdNo);
		map.put(OL_UPD_SIMEI.name(),olUpdSimei);
		map.put(OL_UPD_BUSHO_CD.name(),olUpdBushoCd);
		map.put(MONEY.name(),money);
		map.put(BAT_UPD_PRG_ID.name(),batUpdPrgId);
		map.put(CRE_SIMEI_CD.name(),creSimeiCd);
		map.put(OL_UPD_PRG_ID.name(),olUpdPrgId);
		map.put(BAT_UPD_DATE.name(),batUpdDate);
		map.put(PROC_STS_KBN.name(),procStsKbn);
		map.put(OL_UPD_BUSHO_NM.name(),olUpdBushoNm);
		map.put(LOCK_SEQNO.name(),lockSeqno);
		map.put(CRE_BUSHO_NM.name(),creBushoNm);
		map.put(VALUE.name(),value);
		map.put(OL_UPD_DATE.name(),olUpdDate);
		map.put(OL_UPD_SIMEI_CD.name(),olUpdSimeiCd);
		return map;
	}
	
	/**
	 * @see kosmos.framework.base.AbstractBean#clone()
	 */
	@Override
	public DailyReportSub clone() {
		DailyReportSub clone = new DailyReportSub();
		clone.edNo = edNo;
		clone.eigyKejoYm = eigyKejoYm;
		clone.accumTrnNumber = accumTrnNumber;
		clone.rowNo = rowNo;
		clone.ymd = ymd;
		clone.crePrgId = crePrgId;
		clone.creBushoCd = creBushoCd;
		clone.trnFixStsKbn = trnFixStsKbn;
		clone.shrKbn = shrKbn;
		clone.parentAccumTrnNumber = parentAccumTrnNumber;
		clone.creSimei = creSimei;
		clone.datEntMtKbn = datEntMtKbn;
		clone.procStsKbn2 = procStsKbn2;
		clone.delKbn = delKbn;
		clone.updTermId = updTermId;
		clone.creDate = creDate;
		clone.parentEdNo = parentEdNo;
		clone.olUpdSimei = olUpdSimei;
		clone.olUpdBushoCd = olUpdBushoCd;
		clone.money = money;
		clone.batUpdPrgId = batUpdPrgId;
		clone.creSimeiCd = creSimeiCd;
		clone.olUpdPrgId = olUpdPrgId;
		clone.batUpdDate = batUpdDate;
		clone.procStsKbn = procStsKbn;
		clone.olUpdBushoNm = olUpdBushoNm;
		clone.lockSeqno = lockSeqno;
		clone.creBushoNm = creBushoNm;
		clone.value = value;
		clone.olUpdDate = olUpdDate;
		clone.olUpdSimeiCd = olUpdSimeiCd;		
		return clone;
	}
}

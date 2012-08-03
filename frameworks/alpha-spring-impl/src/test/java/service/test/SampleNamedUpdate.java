/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.Date;

import client.sql.free.AbstractNamedModifyQuery;




/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNamedUpdate extends AbstractNamedModifyQuery{

	public SampleNamedUpdate() {
		getParameter().setSql("@/sql/SAMPLE_UPDATE.sql");
	}
	
	public enum Bind {
		test,
		attr,
		attr2set,
		dateCol
	}
	public enum Branch {
		test,
		attr,
		attr2,
		arc
	}

	/**
	 * @param value
	 */
	public SampleNamedUpdate setTest(Object value){		
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr(Object value){
		setParameter(Bind.attr.name(), value);		
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr2(Object value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setArc(Object value){
		setParameter(Branch.arc.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr2set(Object value){
		setParameter(Bind.attr2set.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setDateCol(Date value){
		setParameter(Bind.dateCol.name(), value);
		return this;
	}

	

}

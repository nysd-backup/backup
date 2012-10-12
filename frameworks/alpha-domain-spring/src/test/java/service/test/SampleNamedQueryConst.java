/**
 * Copyright 2011 the original author
 */
package service.test;

import alpha.sqlclient.free.AbstractNamedReadQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNamedQueryConst extends AbstractNamedReadQuery{
	
	public SampleNamedQueryConst(){
		getParameter().setSql("@SAMPLE_QUERY_CONST.sql");
	}

	public enum Bind {
		test,
		attr,
	}
	public enum Branch {
		test,
		attr,
		attr2,
		arc
	}
	
	public Enum<?>[] getParameterNames() {
		return Bind.values();
	}
	
	public Enum<?>[] getBranchParameterNames() {
		return Branch.values();
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQueryConst setTest(Object value){
		//setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQueryConst setAttr(Object value){
		setParameter(Bind.attr.name(), value);
		//setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedQueryConst setAttr2(Object value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQueryConst setArc(Object value){
		setParameter(Branch.arc.name(), value);
		return this;
	}

}

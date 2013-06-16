/**
 * Copyright 2011 the original author
 */
package service.query;

import org.coder.alpha.query.free.query.AbstractReadQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNativeQueryConst extends AbstractReadQuery{
	
	public SampleNativeQueryConst(){
		getParameter().setSql("@SAMPLE_NATIVE_QUERY_CONST.sql");
		getParameter().setResultType(SampleNativeResult.class);
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
	public SampleNativeQueryConst setTest(Object value){
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQueryConst setAttr(Object value){
		setParameter(Bind.attr.name(), value);		
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeQueryConst setAttr2(Object value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQueryConst setArc(Object value){
		setParameter(Branch.arc.name(), value);
		return this;
	}

}

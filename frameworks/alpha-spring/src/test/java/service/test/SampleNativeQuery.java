/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.List;

import alpha.query.free.AbstractNativeReadQuery;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNativeQuery extends AbstractNativeReadQuery{
	
	public SampleNativeQuery(){
		getParameter().setSql("@SAMPLE_NATIVE_QUERY.sql");
		getParameter().setResultType(SampleNativeResult.class);
	}

	public enum Bind {
		test,
		attr,
		attrs,
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
	public SampleNativeQuery setTest(String value){
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQuery setAttr(String value){
		setParameter(Bind.attr.name(), value);		
		return this;
	}

	
	/**
	 * @param value
	 */
	public SampleNativeQuery setAttrs(List<String> value){
		setParameter(Bind.attrs.name(), value);		
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeQuery setAttr2(Integer value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQuery setArc(String value){
		setParameter(Branch.arc.name(), value);
		return this;
	}


	
}

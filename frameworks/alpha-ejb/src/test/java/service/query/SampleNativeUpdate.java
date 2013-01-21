/**
 * Copyright 2011 the original author
 */
package service.query;

import alpha.query.free.AbstractNativeModifyQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNativeUpdate extends AbstractNativeModifyQuery{
	
	public SampleNativeUpdate(){
		getParameter().setSql("@SAMPLE_NATIVE_UPDATE.sql");
	}

	public enum Bind {
		test,
		attr,
		attr2set
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
	public SampleNativeUpdate setTest(String value){
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr(String value){
		setParameter(Bind.attr.name(), value);		
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr2(Integer value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setArc(Object value){
		setParameter(Branch.arc.name(), value);
		return this;
	}

	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr2set(Integer value){
		setParameter(Bind.attr2set.name(), value);
		return this;
	}
}

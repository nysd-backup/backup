/**
 * Copyright 2011 the original author
 */
package service.test;

import client.sql.free.AbstractNativeReadQuery;
import client.sql.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_QUERY_CONST.sql",resultClass=SampleNativeResult.class)
public class SampleNativeQueryConst extends AbstractNativeReadQuery{

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

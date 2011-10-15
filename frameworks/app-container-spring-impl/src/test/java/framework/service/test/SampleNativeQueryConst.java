/**
 * Copyright 2011 the original author
 */
package framework.service.test;

import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_QUERY_CONST.sql",resultClass=SampleNativeResult.class)
public class SampleNativeQueryConst extends AbstractNativeQuery{

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
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQueryConst setAttr(Object value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeQueryConst setAttr2(Object value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQueryConst setArc(Object value){
		setBranchParameter(Branch.arc.name(), value);
		return this;
	}

}

/**
 * Copyright 2011 the original author
 */
package service.test;

import client.sql.free.AbstractNativeSelect;
import client.sql.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_QUERY.sql",resultClass=SampleNativeResult.class)
public class SampleNativeQuery extends AbstractNativeSelect{

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
	public SampleNativeQuery setTest(String value){
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQuery setAttr(String value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeQuery setAttr2(Integer value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeQuery setArc(String value){
		setBranchParameter(Branch.arc.name(), value);
		return this;
	}


	
}

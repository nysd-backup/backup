/**
 * Copyright 2011 the original author
 */
package service.test;

import client.sql.free.AbstractNamedReadQuery;
import client.sql.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_QUERY.sql")
public class SampleNamedQuery extends AbstractNamedReadQuery{

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
	public SampleNamedQuery setTest(String value){	
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQuery setAttr(String value){
		setParameter(Bind.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedQuery setAttr2(Integer value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQuery setArc(String value){
		setParameter(Branch.arc.name(), value);
		return this;
	}

}

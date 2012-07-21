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
@AnonymousQuery(query="@/sql/SAMPLE_QUERY_CONST.sql")
public class SampleNamedQueryConst extends AbstractNamedReadQuery{

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

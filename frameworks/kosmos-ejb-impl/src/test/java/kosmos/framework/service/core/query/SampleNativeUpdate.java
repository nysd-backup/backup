/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.sqlclient.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_UPDATE.sql")
public class SampleNativeUpdate extends AbstractNativeUpdate{

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
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr(String value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr2(Integer value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setArc(Object value){
		setBranchParameter(Branch.arc.name(), value);
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

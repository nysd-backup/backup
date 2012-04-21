/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import java.util.Date;

import kosmos.framework.sqlclient.free.AbstractNamedUpdate;
import kosmos.framework.sqlclient.free.AnonymousQuery;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_UPDATE.sql")
public class SampleNamedUpdate extends AbstractNamedUpdate{

	public enum Bind {
		test,
		attr,
		attr2set,
		dateCol
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
	public SampleNamedUpdate setTest(Object value){
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr(Object value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr2(Object value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setArc(Object value){
		setBranchParameter(Branch.arc.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setAttr2set(Object value){
		setParameter(Bind.attr2set.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedUpdate setDateCol(Date value){
		setParameter(Bind.dateCol.name(), value);
		return this;
	}

	

}

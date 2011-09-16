/**
 * Use is subject to license terms.
 */
package framework.service.test;

import framework.service.ext.query.EclipseLinkNamedQuery;
import framework.sqlclient.api.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/04/24 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_QUERY_CONST.sql")
public class SampleNamedQueryConst extends EclipseLinkNamedQuery{

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
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQueryConst setAttr(Object value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedQueryConst setAttr2(Object value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQueryConst setArc(Object value){
		setBranchParameter(Branch.arc.name(), value);
		return this;
	}

}
/**
 * Use is subject to license terms.
 */
package framework.service.test;

import framework.service.test.entity.TestEntity;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_QUERY.sql",resultClass=TestEntity.class)
public class SampleNativeQuery extends AbstractNativeQuery{

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
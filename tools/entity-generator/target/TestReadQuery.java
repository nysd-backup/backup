/**
 * Use is subject to license terms.
 */
package ;

import alpha.framework.sqlclient.free.AbstractNativeReadQuery;

/**
 * Query Entity of TestReadQuery
 *
 * @author Tool Auto Making
 */
@AnonymousQuery(query="@/Test.sql",resultClass=TestReadQueryResult.class)
@Generated("alpha.tool.entity-generator")
public class TestReadQuery extends AbstractNativeReadQuery {

	/**
	 * @param b the b to set
	 */
	public TestReadQuery setB(Object b){		
		setParameter("b",b);		
		return this;
	}
	/**
	 * @param ates the ates to set
	 */
	public TestReadQuery setAtes(Object ates){		
		setParameter("ates",ates);		
		return this;
	}
	/**
	 * @param arc the arc to set
	 */
	public TestReadQuery setArc(Object arc){		
		setParameter("arc",arc);		
		return this;
	}
	/**
	 * @param test the test to set
	 */
	public TestReadQuery setTest(Object test){		
		setParameter("test",test);		
		return this;
	}
	/**
	 * @param attr the attr to set
	 */
	public TestReadQuery setAttr(Object attr){		
		setParameter("attr",attr);		
		return this;
	}		

}

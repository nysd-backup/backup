/**
 * Use is subject to license terms.
 */
package ;

import alpha.framework.sqlclient.free.AbstractNamedReadQuery;

/**
 * Query Entity of TestNamedReadQuery
 *
 * @author Tool Auto Making
 */
@AnonymousQuery(query="@/Test.jpql",resultClass=TestNamedReadQueryResult.class)
@Generated("alpha.tool.entity-generator")
public class TestNamedReadQuery extends AbstractNamedReadQuery {

	/**
	 * @param b the b to set
	 */
	public TestNamedReadQuery setB(Object b){		
		setParameter("b",b);		
		return this;
	}
	/**
	 * @param ates the ates to set
	 */
	public TestNamedReadQuery setAtes(Object ates){		
		setParameter("ates",ates);		
		return this;
	}
	/**
	 * @param arc the arc to set
	 */
	public TestNamedReadQuery setArc(Object arc){		
		setParameter("arc",arc);		
		return this;
	}
	/**
	 * @param test the test to set
	 */
	public TestNamedReadQuery setTest(Object test){		
		setParameter("test",test);		
		return this;
	}
	/**
	 * @param attr the attr to set
	 */
	public TestNamedReadQuery setAttr(Object attr){		
		setParameter("attr",attr);		
		return this;
	}		

}

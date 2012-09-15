/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.HashMap;

import alpha.jdbc.domain.AccessorDeclared;





/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AccessorDeclared
public class SampleNativeResult extends HashMap<String,Object>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param test the test to set
	 */
	public SampleNativeResult setTest(String test) {
		put("test",test);
		return this;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return (String)get("test");
	}

	/**
	 * @param attr the attr to set
	 */
	public SampleNativeResult setAttr(String attr) {
		put("attr",attr);
		return this;
	}

	/**
	 * @return the atstr
	 */
	public String getAttr() {
		return (String)get("attr");
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public SampleNativeResult setAttr2(int attr2) {
		put("attr2",attr2);
		return this;
	}

	/**
	 * @return the attr2
	 */
	public Integer getAttr2() {
		return (Integer)get("attr2");
	}

	/**
	 * @param version the version to set
	 */
	public SampleNativeResult setVersion(int version) {
		put("version",version);
		return this;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return (Integer)get("version");
	}
}

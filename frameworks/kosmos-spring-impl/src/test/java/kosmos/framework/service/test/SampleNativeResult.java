/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleNativeResult{
	
	private String test;

	private String attr;
	
	private int attr2;

	private int version;
	
	/**
	 * @param test the test to set
	 */
	public SampleNativeResult setTest(String test) {
		this.test = test;
		return this;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param attr the attr to set
	 */
	public SampleNativeResult setAttr(String attr) {
		this.attr = attr;
		return this;
	}

	/**
	 * @return the atstr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public SampleNativeResult setAttr2(int attr2) {
		this.attr2 = attr2;
		return this;
	}

	/**
	 * @return the attr2
	 */
	public int getAttr2() {
		return attr2;
	}

	/**
	 * @param version the version to set
	 */
	public SampleNativeResult setVersion(int version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
}

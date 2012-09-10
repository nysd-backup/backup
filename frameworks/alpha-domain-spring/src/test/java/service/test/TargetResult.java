/**
 * Copyright 2011 the original author
 */
package service.test;

import javax.persistence.NamedNativeQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@NamedNativeQuery(query="select * from testa",resultClass=TargetResult.class,name="named")
public class TargetResult {

	private String test;

	private String attr;
	
	private int attr2;
	
	private int version;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public int getAttr2() {
		return attr2;
	}

	public void setAttr2(int attr2) {
		this.attr2 = attr2;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}

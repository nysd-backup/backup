/**
 * Copyright 2011 the original author
 */
package service.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Embeddable
public class EmbeddPK {

	@Column(name="KEY_ABC")
	private String keyAbc;
	
	@Column(name="KEY_DEF")
	private String keyDef;

	/**
	 * @return the keyAbc
	 */
	public String getKeyAbc() {
		return keyAbc;
	}

	/**
	 * @param keyAbc the keyAbc to set
	 */
	public void setKeyAbc(String keyAbc) {
		this.keyAbc = keyAbc;
	}

	/**
	 * @return the keyDef
	 */
	public String getKeyDef() {
		return keyDef;
	}

	/**
	 * @param keyDef the keyDef to set
	 */
	public void setKeyDef(String keyDef) {
		this.keyDef = keyDef;
	}
	
	
	
}

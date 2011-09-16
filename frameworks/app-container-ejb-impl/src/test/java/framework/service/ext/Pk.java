/**
 * Use is subject to license terms.
 */
package framework.service.ext;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import framework.core.entity.AbstractEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/02/21 created.
 */
@Embeddable
public class Pk extends AbstractEntity{

	/** EmbeddableIdで使用する場合には@Idはつけてはいけない */
	@Column
	private String test = null;

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}
}

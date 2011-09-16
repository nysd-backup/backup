/**
 * Use is subject to license terms.
 */
package framework.service.ext;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import framework.core.entity.AbstractEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/02/25 created.
 */
@Entity
@Table(name="SAMPLE")
public class EmbeddedIdEntity extends AbstractEntity{

	@EmbeddedId
	private Pk pk;

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Pk pk) {
		this.pk = pk;
	}

	/**
	 * @return the pk
	 */
	public Pk getPk() {
		return pk;
	}
}

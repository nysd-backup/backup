/**
 * Use is subject to license terms.
 */
package framework.service.ext;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/02/25 created.
 */
@Entity
@Table(name="Mapped")
public class SubEntity extends SuperEntity{

	@Column
	private BigDecimal field;

	/**
	 * @param field the field to set
	 */
	public void setField(BigDecimal field) {
		this.field = field;
	}

	/**
	 * @return the field
	 */
	public BigDecimal getField() {
		return field;
	}


}

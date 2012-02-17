package kosmos.framework.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class AbstractBean implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object e){
		return EqualsBuilder.reflectionEquals(this, e);
	}
	
	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AbstractBean clone() {
		try{
			AbstractBean clone = (AbstractBean)super.clone();
			return clone;
		}catch(CloneNotSupportedException cnse){
			throw new IllegalStateException(cnse);
		}
	}
}

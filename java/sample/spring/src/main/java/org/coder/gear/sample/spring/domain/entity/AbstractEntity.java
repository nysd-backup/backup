/**
 * 
 */
package org.coder.gear.sample.spring.domain.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ドメイン層のエンティティの規定 .
 * 
 * @author yoshida-n
 *
 */
public abstract class AbstractEntity {

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

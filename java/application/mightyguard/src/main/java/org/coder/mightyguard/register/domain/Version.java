/**
 * 
 */
package org.coder.mightyguard.register.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author yoshida-n
 *
 */
@XmlRootElement
public class Version {

	public String version;
	
	public String date;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

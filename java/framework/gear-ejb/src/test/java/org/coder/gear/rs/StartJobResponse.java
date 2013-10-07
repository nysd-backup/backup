/**
 * 
 */
package org.coder.gear.rs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yoshida-n
 *
 */
@XmlRootElement
public class StartJobResponse {

	private String rtnCd = null;

	public String getRtnCd() {
		return rtnCd;
	}

	public void setRtnCd(String rtnCd) {
		this.rtnCd = rtnCd;
	}
	
	
}

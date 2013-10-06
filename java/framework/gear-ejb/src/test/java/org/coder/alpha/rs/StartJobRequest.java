/**
 * 
 */
package org.coder.alpha.rs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yoshida-n
 *
 */
@XmlRootElement
public class StartJobRequest {

	private String requestId = null;
	
	private String jobId = null;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	
}

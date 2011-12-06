/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.flat;

import org.supercsv.io.ICsvListWriter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FlatFileRequest {
	
	private ICsvListWriter opendWriter = null;

	private RequestTarget[] targets = null;

	/**
	 * @return the targets
	 */
	public RequestTarget[] getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(RequestTarget[] targets) {
		this.targets = targets;
	}

	/**
	 * @return the opendWriter
	 */
	public ICsvListWriter getOpendWriter() {
		return opendWriter;
	}

	/**
	 * @param opendWriter the opendWriter to set
	 */
	public void setOpendWriter(ICsvListWriter opendWriter) {
		this.opendWriter = opendWriter;
	}

}

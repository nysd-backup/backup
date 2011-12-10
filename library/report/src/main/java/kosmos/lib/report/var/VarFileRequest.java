/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class VarFileRequest {
	
	private VarFileWriter opendWriter = null;

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
	public void setTargets(RequestTarget... targets) {
		this.targets = targets;
	}

	/**
	 * @return the opendWriter
	 */
	public VarFileWriter getOpendWriter() {
		return opendWriter;
	}

	/**
	 * @param opendWriter the opendWriter to set
	 */
	public void setOpendWriter(VarFileWriter opendWriter) {
		this.opendWriter = opendWriter;
	}

}

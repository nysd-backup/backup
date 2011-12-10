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
public class RequestTarget {
	
	private int maxSize = 0;

	private OverLimitHandler overLimitHandler = null;
	
	private EmptyHandler emptyHandler = null;
	
	private Metadata[] includes = null;
	
	private DataCommand<?> command;
	
	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize(){
		return this.maxSize;
	}

	/**
	 * @return the overLimitHandler
	 */
	public OverLimitHandler getOverLimitHandler() {
		return overLimitHandler;
	}

	/**
	 * @param overLimitHandler the overLimitHandler to set
	 */
	public void setOverLimitHandler(OverLimitHandler overLimitHandler) {
		this.overLimitHandler = overLimitHandler;
	}

	/**
	 * @return the emptyHandler
	 */
	public EmptyHandler getEmptyHandler() {
		return emptyHandler;
	}

	/**
	 * @param emptyHandler the emptyHandler to set
	 */
	public void setEmptyHandler(EmptyHandler emptyHandler) {
		this.emptyHandler = emptyHandler;
	}

	/**
	 * @return the command
	 */
	public DataCommand<?> getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(DataCommand<?> command) {
		this.command = command;
	}

	/**
	 * @return the includes
	 */
	public Metadata[] getIncludes() {
		return includes;
	}

	/**
	 * @param includes the includes to set
	 */
	public void setIncludes(Metadata... includes) {
		this.includes = includes;
	}


}

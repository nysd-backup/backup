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
public class Metadata {

	private String name;
	
	private String format ;
	
	private boolean zeroEscape;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the zeroEscape
	 */
	public boolean isZeroEscape() {
		return zeroEscape;
	}

	/**
	 * @param zeroEscape the zeroEscape to set
	 */
	public void setZeroEscape(boolean zeroEscape) {
		this.zeroEscape = zeroEscape;
	}
}

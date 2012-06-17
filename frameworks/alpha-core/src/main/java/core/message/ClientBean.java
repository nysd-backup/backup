/**
 * Copyright 2011 the original author
 */
package core.message;

import java.io.Serializable;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ClientBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String componentId = null;
	
	private long rowIndex = -1;
	
	private String namingContainerId = null;

	/**
	 * @return the componentId
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId the componentId to set
	 */
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return the rowIndex
	 */
	public long getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(long rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the namingContainerId
	 */
	public String getNamingContainerId() {
		return namingContainerId;
	}

	/**
	 * @param namingContainerId the namingContainerId to set
	 */
	public void setNamingContainerId(String namingContainerId) {
		this.namingContainerId = namingContainerId;
	}
}

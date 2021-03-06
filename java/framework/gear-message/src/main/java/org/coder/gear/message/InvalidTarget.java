package org.coder.gear.message;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Error target.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class InvalidTarget implements Serializable {

	/** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     *  the component id.
     */
    private String componentId = null;

    /**
     * the row number.
     */
    private int rowNum = -1;

    /**
     * the containerId
     */
    private String containerId = null;
 
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
	 * @return the rowNum
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * @return the containerId
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 * @param containerId the containerId to set
	 */
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	/**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(containerId).append(",").append(componentId).append(",")
                .append(rowNum);
        return builder.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    
    public static class Builder {
        /**
         *  the component id.
         */
        private String componentId = null;

        /**
         * the row number.
         */
        private int rowNum = -1;

        /**
         * the containerId
         */
        private String containerId = null;

        /**
         * @param componentId
         *            to set
         */
        public static Builder targetFor(String componentId) {
            return new Builder(componentId);
        }
        
        /**
    	 * @param componentId to set
    	 */
    	private Builder(String componentId){
    		this.componentId = componentId;
    	}


        /**
         * <pre>
         *    for container .
         * </pre>
         * 
         * @param rowNum
         *            to set
         * @param tableId
         *            to set
         * @return self
         */
        public Builder withRowNum(int rowNum, String containerId) {
            this.rowNum = rowNum;
            this.containerId =containerId;
            return this;
        }

        /**
         * <pre>
         *   Builds the target .
         * </pre>
         * 
         * @return the target
         */
        public InvalidTarget build() {
            InvalidTarget target = new InvalidTarget();
            target.setComponentId(componentId);
            target.setRowNum(rowNum);
            target.setContainerId(containerId);
            return target;
        }
    }
    
}

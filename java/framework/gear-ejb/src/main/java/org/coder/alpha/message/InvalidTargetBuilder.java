package org.coder.alpha.message;

/**
 * InvalidTargetBuilder.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class InvalidTargetBuilder {

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
    public static InvalidTargetBuilder targetFor(String componentId) {
        return new InvalidTargetBuilder(componentId);
    }
    
    /**
	 * @param componentId to set
	 */
	private InvalidTargetBuilder(String componentId){
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
    public InvalidTargetBuilder withRowNum(int rowNum, String containerId) {
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

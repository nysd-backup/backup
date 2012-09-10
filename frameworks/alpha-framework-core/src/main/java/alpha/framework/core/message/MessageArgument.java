/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The message bean.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageArgument implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<TargetClient> targetClients = new ArrayList<TargetClient>();

	/** the message */
	private final String messageId;
	
	/** the arguments of the message */
	private final Object[] arguments;

	/**
	 * @param message
	 * @param arguments
	 */
	public MessageArgument(String messageId ,Object... arguments){
		this.messageId = messageId;
		this.arguments = arguments;
	}
	
	/**
	 * @return the message
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}
	
	/**
	 * @param namingContainerId
	 * @param rowIndex
	 * @param componentId
	 */
	public void addComponents(String namingContainerId ,long rowIndex,String componentId){
		TargetClient bean = new TargetClient();
		bean.setComponentId(componentId);
		bean.setNamingContainerId(namingContainerId);
		bean.setRowIndex(rowIndex);
		targetClients.add(bean);
	}
	
	/**
	 * @param componentId
	 */
	public void addComponents(String componentId){
		addComponents(null,-1,componentId);
	}

	/**
	 * @return the targetClients
	 */
	public List<TargetClient> getTargetClients() {
		return targetClients;
	}

	/**
	 * @param targetClients the targetClients to set
	 */
	public void setTargetClients(List<TargetClient> targetClients) {
		this.targetClients = targetClients;
	}

}

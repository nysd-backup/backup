/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The message bean.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ClientBean> clientBean = new ArrayList<ClientBean>();

	/** the message */
	private final String messageId;
	
	/** the arguments of the message */
	private final Object[] arguments;

	/**
	 * @param message
	 * @param arguments
	 */
	public MessageBean(String messageId ,Object... arguments){
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
	 * @return
	 */
	public List<ClientBean> getClientBean() {
		return clientBean;
	}
	
	/**
	 * @param namingContainerId
	 * @param rowIndex
	 * @param componentId
	 */
	public void addComponents(String namingContainerId ,long rowIndex,String componentId){
		ClientBean bean = new ClientBean();
		bean.setComponentId(componentId);
		bean.setNamingContainerId(namingContainerId);
		bean.setRowIndex(rowIndex);
		clientBean.add(bean);
	}
	
	/**
	 * @param componentId
	 */
	public void addComponents(String componentId){
		addComponents(null,-1,componentId);
	}

}

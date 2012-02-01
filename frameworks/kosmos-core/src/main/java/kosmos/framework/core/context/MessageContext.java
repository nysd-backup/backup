/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.context;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.core.message.MessageResult;

/**
 * The message context.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageContext {

	/** the list of the messages */
	private List<MessageResult> globalMessageList = new ArrayList<MessageResult>();
	
	/** the thread local instance*/
	private static ThreadLocal<MessageContext> instance = new ThreadLocal<MessageContext>(){
		protected MessageContext initialValue() {
			return null;
		}
	};
	
	/**
	 * @return the current instance
 	 */
	public static MessageContext getCurrentInstance(){
		return instance.get();
	}

	/**
	 * @param context the context to set
	 */
	private static void setCurrentInstance(MessageContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @param message the message to be added to
	 */
	void addMessage(MessageResult message){
		globalMessageList.add(message);
	}
	
	/**
	 * @return the globalMessageList
	 */
	public List<MessageResult> getMessageList(){
		return this.globalMessageList;
	}
	
	/**
	 * @return the globalMessageList
	 */
	public MessageResult[] getMessageArray(){
		 List<MessageResult> list = globalMessageList;
		 return list.toArray(new MessageResult[0]);
	}
	
	/**
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
	}
	
	/**
	 * Releases the context
	 */
	public void release(){
		globalMessageList.clear();		
		setCurrentInstance(null);
	}
}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kosmos.framework.core.message.MessageResult;

/**
 * The message context.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageContext {
	
	/** the user locale */
	private Locale locale = Locale.getDefault();
	
	/** the max level of message */
	private int maximumLevel = 0;
	
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
	public void addMessage(MessageResult message){
		if(maximumLevel < message.getLevel()){
			maximumLevel = message.getLevel();
		}
		globalMessageList.add(message);
	}
	
	/**
	 * @return the locale
	 */
	public Locale getLocale(){
		return this.locale; 
	}
	
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	/**
	 * @return maximum level
	 */
	public int getMaxLevel(){
		return maximumLevel;
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
		locale = Locale.getDefault();
		setCurrentInstance(this);
	}
	
	/**
	 * Releases the context
	 */
	public void release(){
		maximumLevel = 0;
		locale = null;
		globalMessageList.clear();		
		setCurrentInstance(null);
	}
}

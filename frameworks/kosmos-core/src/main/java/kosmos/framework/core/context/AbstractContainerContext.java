/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kosmos.framework.core.message.MessageResult;


/**
 * The thread-local context
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractContainerContext {

	/** the level of call stack */
	protected int callStackLevel = 0;
	
	/** the list of the messages */
	protected List<MessageResult> globalMessageList = new ArrayList<MessageResult>();
	
	/** the locale */
	protected Locale locale = Locale.getDefault();
	
	/** the thread local instance*/
	private static ThreadLocal<AbstractContainerContext> instance = new ThreadLocal<AbstractContainerContext>(){
		protected AbstractContainerContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(AbstractContainerContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @return the current context
	 */
	public static AbstractContainerContext getCurrentInstance(){
		return instance.get();
	}
	
	/**
	 * @param message the message to be added to
	 */
	public void addMessage(MessageResult message){
		globalMessageList.add(message);
	}
	
	/**
	 * push call stack.
	 */
	public void pushCallStack(){
		callStackLevel++;
	}
	
	/**
	 * pop call stack.
	 */
	public void popCallStack(){
		callStackLevel--;
	}
	
	/**
	 * @param locale the locale
	 */
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	/**
	 * @return the locale
	 */
	public Locale getLocale(){
		return locale;
	}
	
	/**
	 * @return the level of the call stack
	 */
	public int getCallStackLevel(){
		return callStackLevel;
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
	 * Releases the context
	 */
	public void release(){
		globalMessageList.clear();
		callStackLevel = 0;
		locale = Locale.getDefault();
	}

}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

import java.io.Serializable;
import java.util.Locale;

/**
 * The message bean.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** the message */
	private final AbstractMessage message;
	
	/** the arguments of the message */
	private final Object[] arguments;
	
	/** the locale */
	private final Locale locale;

	/**
	 * @param message
	 * @param arguments
	 */
	public MessageBean(AbstractMessage message ,Locale locale, Object... arguments){
		this.message = message;
		this.locale = locale;
		this.arguments = arguments;
	}
	

	/**
	 * @param message
	 * @param arguments
	 */
	public MessageBean(AbstractMessage message, Object... arguments){
		this(message,Locale.getDefault(),arguments);
	}
	
	/**
	 * @param message
	 */
	public MessageBean(AbstractMessage message){
		this(message,Locale.getDefault());
	}
	
	/**
	 * @return the message
	 */
	public AbstractMessage getMessage() {
		return message;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

}

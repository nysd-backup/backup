/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.object;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageBuilder {

	private final String messageId;
	
	private int rowNumber;
	
	private Object[] args;
	
	private String baseName = "META-INF/application_message";
	
	private Locale locale = Locale.getDefault();
	
	private String rowNumberSuffix = "";
	
	/**
	 * @param messageId to set
	 * @return self
	 */
	public static MessageBuilder with(String messageId){
		return new MessageBuilder(messageId);
	}
	
	/**
	 * @param messageId to set
	 */
	private MessageBuilder(String messageId){
		this.messageId = messageId;
	}
	
	public MessageBuilder withRowNumberSuffix(String rowNumberSuffix){
		this.rowNumberSuffix = rowNumberSuffix;
		return this;
	}
	
	public MessageBuilder withBaseName(String baseName){
		this.baseName = baseName;
		return this;
	}
	
	public MessageBuilder withLocale(Locale locale){
		this.locale = locale;
		return this;
	}

	/**
	 * @param rowNumber to set
	 * @return self
	 */
	public MessageBuilder withRowNumber(int rowNumber){
		this.rowNumber = rowNumber;
		return this;
	}
	
	/**
	 * @param args to set
	 * @return self
	 */
	public MessageBuilder withArgs(Object... args){
		this.args = args;
		return this;
	}
	
	/**
	 * @return the message
	 */
	public Message build(){		
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        String message = bundle.getString(messageId);
        String[] splited = message.split(",");
        Message result = new Message();
        result.setMessageId(splited[0]);
        List<Object> arg = new ArrayList<Object>();
        if (rowNumber > 0) {
            arg.add(rowNumber + rowNumberSuffix);
        } else {
            arg.add("");
        }
        if (args != null) {
            arg.addAll(Arrays.asList(args));
        }
        result.setMessage(MessageFormat.format(splited[1],
                arg.toArray()));
        result.setMessageLevel(MessageLevel.valueOf(splited[2])
                .ordinal());
        if (splited.length > 3) {
            result.setNotifiable("Y".equals(splited[3]));
        }
        return result;
	}
}

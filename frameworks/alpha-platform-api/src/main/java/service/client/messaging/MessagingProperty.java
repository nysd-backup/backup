/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.util.HashMap;

/**
 * MessagingProperty.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessagingProperty extends HashMap<String,Object>{

	private static final long serialVersionUID = 1L;

	public static final String DYNAMIC_DESTINATION_ALIAS = "alpha.dynamic.destination.aslias";
	
	/**
	 * @param alias the alias to set
	 */
	public void setDynamicDestinationName(String alias){
		put(DYNAMIC_DESTINATION_ALIAS,alias);
	}
	
	/**
	 * @return alias
	 */
	public String getDynamicDestinatonName(){
		return (String)get(DYNAMIC_DESTINATION_ALIAS);
	}
}

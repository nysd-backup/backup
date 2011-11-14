/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.service;

import kosmos.framework.api.dto.RequestDto;

/**
 * Activates the services.
 * 
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface ServiceActivator {

	/**
	 * Activates the services.
	 * 
	 * @param dto the DTO
	 * @return the reply
	 */
	public Object activateAndInvoke(RequestDto dto);
	
}

/**
 * Copyright 2011 the original author
 */
package framework.api.service;

import framework.api.dto.RequestDto;

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
	 */
	public Object activate(RequestDto dto);
	
}

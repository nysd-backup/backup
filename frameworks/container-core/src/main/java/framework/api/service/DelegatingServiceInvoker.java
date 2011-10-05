/**
 * Copyright 2011 the original author
 */
package framework.api.service;

import framework.api.dto.RequestDto;

/**
 * A listener receives the request.
 * 
 * <pre>
 * Remote call and MDB call use this.
 * </pre>
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface DelegatingServiceInvoker {

	/**
	 * @param dto the DTO
	 */
	public Object processService(RequestDto dto);
	
}

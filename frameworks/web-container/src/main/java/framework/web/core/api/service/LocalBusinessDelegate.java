/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;
import framework.api.service.DelegatingServiceInvoker;

/**
 * The LocalBusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocalBusinessDelegate extends AbstractBusinessDelegate{

	/** the listener */
	private DelegatingServiceInvoker listener;

	/**
	 * @param listener the listener to set
	 */
	public void setRequestListener(DelegatingServiceInvoker listener){		
		this.listener = listener;
	}

	/**
	 * @see framework.web.core.api.service.AbstractBusinessDelegate#processService(framework.api.dto.RequestDto)
	 */
	@Override
	protected ReplyDto processService(RequestDto dto) {
		return listener.processService(dto);
	}

	
}

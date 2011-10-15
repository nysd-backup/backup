/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import framework.api.dto.RequestDto;
import framework.api.service.ServiceActivator;

/**
 * The LocalBusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocalBusinessDelegate extends AbstractBusinessDelegate{

	/** the ServiceActivator */
	private ServiceActivator activator;

	/**
	 * @param activator the activator to set
	 */
	public void setServiceActivator(ServiceActivator activator){		
		this.activator = activator;
	}

	/**
	 * @see framework.web.core.api.service.AbstractBusinessDelegate#processService(framework.api.dto.RequestDto)
	 */
	@Override
	protected Object processService(RequestDto dto) {
		return activator.activate(dto);
	}

	
}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.service;

import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ServiceActivator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;


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
	 * @see kosmos.framework.web.core.api.service.AbstractBusinessDelegate#processService(kosmos.framework.api.dto.RequestDto)
	 */
	@Override
	protected Object processService(RequestDto dto) {
		return activator.activate(dto);
	}

	
}

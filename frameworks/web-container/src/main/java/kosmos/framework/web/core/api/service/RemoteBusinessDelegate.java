/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.service;

import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ServiceActivator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;


/**
 * The RemoteBusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RemoteBusinessDelegate extends AbstractBusinessDelegate implements ApplicationContextAware {

	/** the name of the listener */
	private String remoteListenerName = null;

	/** Spring's context */
	private ApplicationContext context = null;
	
	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	/**
	 * @param remoteListenerName the remoteListenerName to set
	 */
	public void setRemoteListenerName(String remoteListenerName){		
		this.remoteListenerName = remoteListenerName;
	}
	/**
	 * @see kosmos.framework.web.core.api.service.AbstractBusinessDelegate#processService(kosmos.framework.api.dto.RequestDto)
	 */
	@Override
	protected Object processService(RequestDto dto) {
		ServiceActivator listener = ServiceActivator.class.cast(context.getBean(remoteListenerName));
		return listener.activate(dto);
	}
	

}

/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;

import framework.api.dto.RequestDto;
import framework.api.service.DelegatingServiceInvoker;

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
	 * @see framework.web.core.api.service.AbstractBusinessDelegate#processService(framework.api.dto.RequestDto)
	 */
	@Override
	protected Object processService(RequestDto dto) {
		DelegatingServiceInvoker listener = DelegatingServiceInvoker.class.cast(context.getBean(remoteListenerName));
		return listener.processService(dto);
	}
	

}

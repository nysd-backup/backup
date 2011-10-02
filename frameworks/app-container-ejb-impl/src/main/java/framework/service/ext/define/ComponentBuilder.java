/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import java.lang.reflect.InvocationHandler;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.service.RequestListener;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.service.core.async.AsyncServiceFactory;
import framework.service.core.messaging.MessageClientFactory;
import framework.sqlclient.api.free.QueryFactory;

/**
 * A Component builder instead of DI container.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return the <code>MessageClientFactory</code>
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	/**
	 * @return the <code>RequestListener</code>
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return the JMS publisher
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return the JMS sender
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return the <code>MessageAccessor</code>
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return the <code>QueryFactory</code>
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return the <code>QueryFactory</code> only called from WEB 
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>AdvancedOrmQueryFactory</code>
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
}

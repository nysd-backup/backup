/**
 * Use is subject to license terms.
 */
package framework.service.ext.define;

import java.lang.reflect.InvocationHandler;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.service.RequestListener;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.service.core.async.AsyncServiceFactory;
import framework.service.core.messaging.MessageClientFactory;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.sqlclient.api.free.QueryFactory;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	
	/**
	 * @return
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return
	 */
	public EntityManagerAccessor createEntityManagerAccessor();

}

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
import framework.service.core.persistence.EntityManagerAccessor;
import framework.sqlclient.api.free.QueryFactory;

/**
 * DI繧ｳ繝ｳ繝・リ縺ｫ莉｣繧上ｊ繧ｳ繝ｳ繝昴・繝阪Φ繝医ｒ逕滓・縺吶ｋ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return 繝｡繝・そ繝ｼ繧ｸ繝ｳ繧ｰ繝輔ぃ繧ｯ繝医Μ
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	
	/**
	 * @return 繝ｪ繧ｯ繧ｨ繧ｹ繝医Μ繧ｹ繝翫・
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return JMS topic騾∽ｿ｡繧ｨ繝ｳ繧ｸ繝ｳ
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return JMS queue騾∽ｿ｡繧ｨ繝ｳ繧ｸ繝ｳ
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return 繝｡繝・そ繝ｼ繧ｸ繧｢繧ｯ繧ｻ繧ｵ
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return 繧ｯ繧ｨ繝ｪ繝輔ぃ繧ｯ繝医Μ
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return WEB螻､縺九ｉ縺ｮ繧ｯ繧ｨ繝ｪ逕ｨ縺ｮ繝輔ぃ繧ｯ繝医Μ
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return 髱槫酔譛溘し繝ｼ繝薙せ繝輔ぃ繧ｯ繝医Μ
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return ORM繧ｯ繧ｨ繝ｪ繝輔ぃ繧ｯ繝医Μ
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return 繧ｨ繝ｳ繝・ぅ繝・ぅ繝槭ロ繝ｼ繧ｸ繝｣繝ｩ繝・ヱ繝ｼ
	 */
	public EntityManagerAccessor createEntityManagerAccessor();

}

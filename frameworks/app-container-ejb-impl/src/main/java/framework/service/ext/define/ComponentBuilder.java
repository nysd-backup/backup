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
 * DIコンテナに代わりコンポーネントを生成する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return メッセージングファクトリ
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	
	/**
	 * @return リクエストリスナー
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return JMS topic送信エンジン
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return JMS queue送信エンジン
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return メッセージアクセサ
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return クエリファクトリ
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return WEB層からのクエリ用のファクトリ
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return 非同期サービスファクトリ
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return ORMクエリファクトリ
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return エンティティマネージャラッパー
	 */
	public EntityManagerAccessor createEntityManagerAccessor();

}

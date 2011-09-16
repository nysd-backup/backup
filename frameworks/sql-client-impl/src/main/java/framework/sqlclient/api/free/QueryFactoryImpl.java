/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import framework.sqlclient.api.ConnectionProvider;
import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.free.AbstractFreeQuery;
import framework.sqlclient.api.free.AbstractUpdate;
import framework.sqlclient.api.free.AnonymousQuery;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.QueryFactory;
import framework.sqlclient.internal.impl.InternalQueryImpl;
import framework.sqlclient.internal.impl.QueryEngineImpl;
import framework.sqlclient.internal.impl.UpdateEngineImpl;
import framework.sqlengine.facade.SQLEngineFacade;
import framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * NativeQueryファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryFactoryImpl implements QueryFactory{
	
	/** コネクション供給者 */
	private ConnectionProvider connectionProvider;
	
	/** 0件時処理 */
	private EmptyHandler emptyHandler;
	
	/** エンジン（デフォルト） */
	private SQLEngineFacade engineFacade = new SQLEngineFacadeImpl();

	/**
	 * @param facade SQLエンジン
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.engineFacade = facade;
	}
	
	/**
	 * @param emptyHandler
	 */
	public void setEmptyHandler(EmptyHandler emptyHandler){
		this.emptyHandler = emptyHandler;
	}
	
	/**
	 * @param connectionProvider コネクション供給者
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider){
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @see framework.sqlclient.api.free.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K extends FreeQuery, T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass) {
		AnonymousQuery aq = queryClass.getAnnotation(AnonymousQuery.class);		

		InternalQueryImpl query = new InternalQueryImpl(false,aq.query(),queryClass.getSimpleName(),connectionProvider, aq.resultClass(),engineFacade);
		K engine = (K)new QueryEngineImpl(query,emptyHandler);
		T q;
		try {
			q = queryClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		q.setDelegate(engine);
		return q;
	}

	/**
	 * @see framework.sqlclient.api.free.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K extends FreeUpdate, T extends AbstractUpdate<K>> T createUpdate(Class<T> updateClass) {
		AnonymousQuery aq = updateClass.getAnnotation(AnonymousQuery.class);		

		InternalQueryImpl query = new InternalQueryImpl(false,aq.query(),updateClass.getSimpleName(),connectionProvider, null,engineFacade);
		K engine = (K)new UpdateEngineImpl(query);
		T q;
		try {
			q = updateClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		q.setDelegate(engine);
		return q;
	}

}

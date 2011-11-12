/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.EmptyHandler;
import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.free.AbstractUpdate;
import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.sqlclient.internal.impl.InternalQueryImpl;
import kosmos.framework.sqlclient.internal.impl.QueryEngineImpl;
import kosmos.framework.sqlclient.internal.impl.UpdateEngineImpl;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * The factory to create the free writable query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryFactoryImpl implements QueryFactory{
	
	/** The ConnectionProvider */
	private ConnectionProvider connectionProvider;
	
	/** the EmptyHandler */
	private EmptyHandler emptyHandler;
	
	/** the SQLEngineFacade */
	private SQLEngineFacade engineFacade = new SQLEngineFacadeImpl();

	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.engineFacade = facade;
	}
	
	/**
	 * @param emptyHandler the emptyHandler to set
	 */
	public void setEmptyHandler(EmptyHandler emptyHandler){
		this.emptyHandler = emptyHandler;
	}
	
	/**
	 * @param connectionProvider the connectionProvider to set
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider){
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
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
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
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

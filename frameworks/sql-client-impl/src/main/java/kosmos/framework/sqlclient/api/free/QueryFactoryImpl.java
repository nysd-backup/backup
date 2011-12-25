/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import javax.persistence.QueryHint;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.free.impl.InternalQueryImpl;
import kosmos.framework.sqlclient.internal.free.impl.LocalQueryEngineImpl;
import kosmos.framework.sqlclient.internal.free.impl.LocalUpdateEngineImpl;
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
	
	/** the SQLEngineFacade */
	private SQLEngineFacade engineFacade = new SQLEngineFacadeImpl();

	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.engineFacade = facade;
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
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery, T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass) {
		AnonymousQuery aq = queryClass.getAnnotation(AnonymousQuery.class);		

		InternalQuery query = new InternalQueryImpl(false,aq.query(),queryClass.getSimpleName(),connectionProvider, aq.resultClass(),engineFacade);
		K engine = (K)new LocalQueryEngineImpl(query);
		for(QueryHint hint : aq.hints()){
			engine.setHint(hint.name(), hint.value());
		}
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
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeUpdate, T extends AbstractUpdate<K>> T createUpdate(Class<T> updateClass) {
		AnonymousQuery aq = updateClass.getAnnotation(AnonymousQuery.class);		

		InternalQuery query = new InternalQueryImpl(false,aq.query(),updateClass.getSimpleName(),connectionProvider, null,engineFacade);
		K engine = (K)new LocalUpdateEngineImpl(query);
		for(QueryHint hint : aq.hints()){
			engine.setHint(hint.name(), hint.value());
		}
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

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.internal.impl.InternalQueryImpl;
import kosmos.framework.sqlclient.internal.impl.QueryEngineImpl;
import kosmos.framework.sqlclient.internal.impl.UpdateEngineImpl;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * The factory to create the query using SQL-Engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NativeQueryFactoryImpl extends AbstractQueryFactory {
	
	/** the connection provider */
	private ConnectionProvider connectionProvider;
	
	/** the facade of the SQL-Engine */
	private SQLEngineFacade engineFacade = new SQLEngineFacadeImpl();

	/**
	 * @param connectionProvider the connectionProvider to set
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider){
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.engineFacade = facade;
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.AbstractQueryFactory#createNativeQueryEngine(java.lang.Class)
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		AnonymousQuery aq = getAnonymousQuery(queryClass);
		InternalQueryImpl internalQuery = new InternalQueryImpl(false,aq.query(),queryClass.getSimpleName(),connectionProvider, aq.resultClass(),engineFacade);
		setHint(queryClass,internalQuery);
		FreeQuery engine = new QueryEngineImpl(internalQuery,emptyHandler);						
		return engine;
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.AbstractQueryFactory#createNativeUpdateEngine(java.lang.Class)
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		AnonymousQuery aq = getAnonymousQuery(updateClass);
		InternalQueryImpl internalQuery = new InternalQueryImpl(false,aq.query(),updateClass.getSimpleName(),connectionProvider, null,engineFacade);
		setHint(updateClass,internalQuery);
		FreeUpdate engine = new UpdateEngineImpl(internalQuery);						
		return engine;
	}

	/**
	 * @param clazz the class
	 * @return the annotation
	 */
	private AnonymousQuery getAnonymousQuery(Class<?> clazz){
		AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);		
		if(aq == null){
			throw new IllegalArgumentException("Annotation 'AnonymousQuery' is required");
		}
		return aq;
		
	}
}

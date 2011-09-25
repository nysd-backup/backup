/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.free;

import framework.sqlclient.api.ConnectionProvider;
import framework.sqlclient.api.free.AnonymousQuery;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.internal.impl.InternalQueryImpl;
import framework.sqlclient.internal.impl.QueryEngineImpl;
import framework.sqlclient.internal.impl.UpdateEngineImpl;
import framework.sqlengine.facade.SQLEngineFacade;
import framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * NativeQuerはJPAではなくSQLエンジンを使用する.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeQueryFactoryImpl extends AbstractQueryFactory {
	
	/** コネクション供給者 */
	private ConnectionProvider connectionProvider;
	
	/** SQLエンジンのファサード */
	private SQLEngineFacade engineFacade = new SQLEngineFacadeImpl();

	/**
	 * @param connectionProvider コネクション供給者
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider){
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @param facade SQLエンジン
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.engineFacade = facade;
	}

	/**
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeQueryEngine(java.lang.Class)
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		AnonymousQuery aq = getAnonymousQuery(queryClass);
		InternalQueryImpl internalQuery = new InternalQueryImpl(false,aq.query(),queryClass.getSimpleName(),connectionProvider, aq.resultClass(),engineFacade);
		FreeQuery engine = new QueryEngineImpl(internalQuery,emptyHandler);						
		return engine;
	}

	/**
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeUpdateEngine(java.lang.Class)
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		AnonymousQuery aq = getAnonymousQuery(updateClass);
		InternalQueryImpl internalQuery = new InternalQueryImpl(false,aq.query(),updateClass.getSimpleName(),connectionProvider, null,engineFacade);
		FreeUpdate engine = new UpdateEngineImpl(internalQuery);						
		return engine;
	}

	/**
	 * @param clazz クラス
	 * @return アノテーション
	 */
	private AnonymousQuery getAnonymousQuery(Class<?> clazz){
		AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);		
		if(aq == null){
			throw new IllegalArgumentException("Annotation 'AnonymousQuery' is required");
		}
		return aq;
		
	}
}

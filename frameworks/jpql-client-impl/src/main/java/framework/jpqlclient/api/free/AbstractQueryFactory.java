/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.free;

import javax.persistence.EntityManager;
import javax.persistence.QueryHint;

import framework.jpqlclient.api.EntityManagerProvider;
import framework.jpqlclient.internal.free.impl.InternalNamedQueryImpl;
import framework.jpqlclient.internal.free.impl.LocalNamedQueryEngine;
import framework.jpqlclient.internal.free.impl.LocalNamedUpdateEngine;
import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.Update;
import framework.sqlclient.api.free.AbstractFreeQuery;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.AbstractNativeUpdate;
import framework.sqlclient.api.free.AbstractUpdate;
import framework.sqlclient.api.free.AnonymousQuery;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.QueryAccessor;
import framework.sqlclient.api.free.QueryFactory;
import framework.sqlclient.internal.impl.DefaultEmptyHandlerImpl;
import framework.sqlengine.builder.ConstAccessor;
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.builder.impl.ConstAccessorImpl;
import framework.sqlengine.builder.impl.SQLBuilderProxyImpl;

/**
 * Queryファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractQueryFactory  implements QueryFactory{
	
	/** エンティティマネージャ */
	protected EntityManager em;
	
	/** 0件時処理 */
	protected EmptyHandler emptyHandler = new DefaultEmptyHandlerImpl();
	
	/** 定数アクセス */
	protected ConstAccessor accessor = new ConstAccessorImpl();
	
	/** SQLビルダー */
	protected SQLBuilder builder = new SQLBuilderProxyImpl();


	/**
	 * @param builder ビルダー
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @param accessor アクセサ
	 */
	public void setConstAccessor(ConstAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @param emptyHandler 0件時処理
	 */
	public void setEmptyHandler(EmptyHandler emptyHandler){
		this.emptyHandler = emptyHandler;
	}

	/**
	 * @param provider プロバイダー
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();
	}

	/**
	 * @see framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeUpdate,T extends AbstractUpdate<K>> T createUpdate(Class<T> updateClass) {
		K delegate = null;
		
		if( AbstractNamedUpdate.class.isAssignableFrom(updateClass)){			
			delegate = (K)createNamedUpdateEngine(updateClass);
		
		}else if(AbstractNativeUpdate.class.isAssignableFrom(updateClass)){
			delegate = (K)createNativeUpdateEngine(updateClass);
		
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + updateClass);
		}
		T update = newInstance(updateClass);
		QueryAccessor.setDelegate(update,delegate);
		return update;
	}


	/**
	 * @see framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass){
		K delegate = null;
		
		if( AbstractNamedQuery.class.isAssignableFrom(queryClass)){
			delegate = (K)createNamedQueryEngine(queryClass);
		
		}else if(AbstractNativeQuery.class.isAssignableFrom(queryClass)){			
			delegate = (K)createNativeQueryEngine(queryClass);
			
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + queryClass);
		}
		T eq = newInstance(queryClass);
		QueryAccessor.setDelegate(eq, delegate);		
		return eq;
	}
	
	/**
	 * @param queryClass　クエリクラス
	 * @param em エンティティマネージャ
	 * @return クエリ
	 */
	protected abstract FreeQuery createNativeQueryEngine(Class<?> queryClass);
	
	/**
	 * @param queryClass　クエリクラス
	 * @param em エンティティマネージャ
	 * @return クエリ
	 */
	protected abstract FreeUpdate createNativeUpdateEngine(Class<?> updateClass);
	
	/**
	 * @param queryClass　クエリクラス
	 * @param em エンティティマネージャ
	 * @return クエリ
	 */
	protected FreeQuery createNamedQueryEngine(Class<?> queryClass){
		return new LocalNamedQueryEngine(getNamedQuery(queryClass),emptyHandler);
	}
	
	/**
	 * @param queryClass　クエリクラス
	 * @param em エンティティマネージャ
	 * @return クエリ
	 */
	protected Update createNamedUpdateEngine(Class<?> updateClass){
		return new LocalNamedUpdateEngine(getNamedQuery(updateClass));
	}
	

	/**
	 * NamedQueryを作成する
	 * @param clazz クラス
	 * @return Queryオブジェクト
	 */
	protected InternalNamedQueryImpl getNamedQuery(Class<?> clazz){
		
		javax.persistence.NamedQuery nq = clazz.getAnnotation(javax.persistence.NamedQuery.class);
		InternalNamedQueryImpl query = null;
		QueryHint[] hints = new QueryHint[0];
		//標準
		if(nq != null){
			query = new InternalNamedQueryImpl(nq.name(),nq.query(), em,clazz.getSimpleName() ,false,builder,accessor);				
			hints = nq.hints();
		//拡張-if文使用	
		}else{
			AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);
			query = new InternalNamedQueryImpl(null,aq.query(), em, clazz.getSimpleName(),false,builder,accessor);				
			Hint hint = clazz.getAnnotation(Hint.class);
			if(hint != null){
				hints = hint.hitns();
			}
		}
		for(QueryHint h: hints){
			query.setHint(h.name(), h.value());
		}
		return query;
	}
	
	
	/**
	 * @param <T>　型
	 * @param clazz クラス
	 * @return インスタンス
	 */
	protected <T> T newInstance(Class<T> clazz){
		try{
			return clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
}

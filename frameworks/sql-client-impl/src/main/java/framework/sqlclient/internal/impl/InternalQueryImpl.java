/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import java.util.List;

import framework.sqlclient.api.ConnectionProvider;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlclient.internal.AbstractInternalQuery;
import framework.sqlengine.facade.QueryParameter;
import framework.sqlengine.facade.QueryResult;
import framework.sqlengine.facade.SQLEngineFacade;
import framework.sqlengine.facade.SQLParameter;
import framework.sqlengine.facade.UpdateParameter;

/**
 * SQLエンジン用冁E��クエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalQueryImpl<T> extends AbstractInternalQuery{
	
	/** コネクション生�E老E*/
	protected final ConnectionProvider cs;
	
	/** 結果格納クラス */
	protected final Class<T> resultType;
	
	/** ResultSet用フィルター */
	protected ResultSetFilter<T> filter;
	
	/** SQLエンジン */
	protected final SQLEngineFacade facade;
	
	/**
	 * @param useRowSql if斁E��価有無
	 * @param sql SQL
	 * @param queryId クエリID
	 * @param cs コネクション提供老E
	 * @param resultType 結果クラス
	 * @param facade SQLEngine
	 */
	public InternalQueryImpl(boolean useRowSql ,String sql , String queryId, ConnectionProvider cs , Class<T> resultType,SQLEngineFacade facade){
		super(useRowSql,sql,queryId);
		this.resultType = resultType;		
		this.cs = cs;
		this.facade = facade;
	}
	
	/**
	 * @param filter　リザルトセチE��フィルター
	 * @return self
	 */
	public InternalQueryImpl<T> setFilter(ResultSetFilter<T> filter){
		this.filter = filter;
		return this;
	}
	
	/**
	 * @return 結果取征E
	 */
	public NativeResult<T> getNativeResult(){
		QueryParameter<T> param = createQueryParameter();
		QueryResult<T> result = facade.executeTotalQuery(param, cs.getConnection());
		return new NativeResult<T>(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @return フェチE��して取征E
	 */
	@SuppressWarnings("rawtypes")
	public List getFetchResult(){
		QueryParameter<T> param = createQueryParameter();
		QueryResult<T> result = facade.executeFetch(param, cs.getConnection());
		return result.getResultList();
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){
		QueryParameter<T> parameter = createParameter(new QueryParameter<T>());
		parameter.setFirstResult(firstResult);
		parameter.setMaxSize(maxSize);
		return facade.executeCount(parameter, cs.getConnection());
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getResultList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getResultList() {
		QueryParameter<T> param = createQueryParameter();
		QueryResult<T> result = facade.executeQuery(param, cs.getConnection());
		return result.getResultList();
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public Object getSingleResult() {
		setMaxResults(1);
		List<?> result = getResultList();
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		UpdateParameter parameter = createParameter(new UpdateParameter());
		return facade.executeUpdate(parameter, cs.getConnection());
	}
	
	/**
	 * @return パラメータ
	 */
	private QueryParameter<T> createQueryParameter(){
		QueryParameter<T> parameter = createParameter(new QueryParameter<T>());		
		parameter.setMaxSize(maxSize);
		parameter.setFirstResult(firstResult);
		parameter.setResultType(resultType);
		
		if(filter != null){
			parameter.setFilter(new DelegatingResultSetFilter<T>(filter));
		}
		return parameter;
	}
	
	/**
	 * @param <S> 垁E
	 * @param parameter パラメータ
	 * @return パラメータ
	 */
	private <S extends SQLParameter> S createParameter(S parameter){
		parameter.setSqlId(queryId);
		parameter.setSql(sql);		
		parameter.setAllParameter(param);
		parameter.setAllBranchParameter(branchParam);
		parameter.setUseRowSql(useRowSql);
		return parameter;
	}

}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlclient.internal.AbstractInternalQuery;
import kosmos.framework.sqlengine.facade.QueryParameter;
import kosmos.framework.sqlengine.facade.QueryResult;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.SQLParameter;
import kosmos.framework.sqlengine.facade.UpdateParameter;


/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalQueryImpl<T> extends AbstractInternalQuery{
	
	/** the ConnectionProvider */
	protected final ConnectionProvider cs;
	
	/** the resultType */
	protected final Class<T> resultType;
	
	/** the filter for <code>ResultSet</code> */
	protected ResultSetFilter<T> filter = null;
	
	/** the facade of SQLEngine */
	protected final SQLEngineFacade facade;
	
	/** the timeout seconds */
	protected int timeoutSeconds = -1;
	
	/**
	 * @param useRowSql if true dont analyze the template
	 * @param sql the SQL
	 * @param queryId the queryId
	 * @param cs the cs
	 * @param resultType the result type
	 * @param facade the facade
	 */
	public InternalQueryImpl(boolean useRowSql ,String sql , String queryId, ConnectionProvider cs , Class<T> resultType,SQLEngineFacade facade){
		super(useRowSql,sql,queryId);
		this.resultType = resultType;		
		this.cs = cs;
		this.facade = facade;
	}
	
	/**
	 * @param filter the filter to set
	 * @return self
	 */
	public void setFilter(ResultSetFilter<T> filter){
		this.filter = filter;
	}
	

	/**
	 * @param seconds the JDBC timeout
	 */
	public void setQueryTimeout(int seconds) {
		this.timeoutSeconds = seconds;		
	}
	
	/**
	 * @return the result
	 */
	public NativeResult<T> getTotalResult(){
		QueryParameter<T> param = createQueryParameter();
		QueryResult<T> result = facade.executeTotalQuery(param, cs.getConnection());
		return new NativeResult<T>(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @return the result holiding the <code>ResultSet</code>
	 */
	@SuppressWarnings("rawtypes")
	public List getFetchResult(){
		QueryParameter<T> param = createQueryParameter();
		return facade.executeFetch(param, cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){
		QueryParameter<T> parameter = createParameter(new QueryParameter<T>());
		parameter.setFirstResult(firstResult);
		parameter.setMaxSize(maxSize);
		return facade.executeCount(parameter, cs.getConnection());
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.AbstractInternalQuery#getResultList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getResultList() {
		QueryParameter<T> param = createQueryParameter();
		return facade.executeQuery(param, cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.AbstractInternalQuery#getSingleResult()
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
	 * @see kosmos.framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		UpdateParameter parameter = createParameter(new UpdateParameter());
		return facade.executeUpdate(parameter, cs.getConnection());
	}
	
	/**
	 * @return the parameter
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
	 * @param <S> the type
	 * @param parameter the parameter
	 * @return the parameter
	 */
	private <S extends SQLParameter> S createParameter(S parameter){
		parameter.setSqlId(queryId);
		parameter.setSql(sql);		
		parameter.setTimeoutSeconds(timeoutSeconds);
		parameter.setAllParameter(param);
		parameter.setAllBranchParameter(branchParam);
		parameter.setUseRowSql(useRowSql);
		return parameter;
	}

}

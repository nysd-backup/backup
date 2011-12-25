/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlclient.internal.free.AbstractInternalQuery;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
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
public class InternalQueryImpl extends AbstractInternalQuery implements InternalQuery{
	
	/** the ConnectionProvider */
	protected final ConnectionProvider cs;
	
	/** the resultType */
	@SuppressWarnings("rawtypes")
	protected final Class resultType;
	
	/** the filter for <code>ResultSet</code> */
	protected ResultSetFilter filter = null;
	
	/** the facade of SQLEngine */
	protected final SQLEngineFacade facade;
	
	/**
	 * @param useRowSql if true dont analyze the template
	 * @param sql the SQL
	 * @param queryId the queryId
	 * @param cs the cs
	 * @param resultType the result type
	 * @param facade the facade
	 */
	@SuppressWarnings("rawtypes")
	public InternalQueryImpl(boolean useRowSql ,String sql , String queryId, ConnectionProvider cs , Class resultType,SQLEngineFacade facade){
		super(useRowSql,sql,queryId);
		this.resultType = resultType;		
		this.cs = cs;
		this.facade = facade;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@Override
	public void setFilter(ResultSetFilter filter){
		this.filter = filter;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getTotalResult()
	 */
	@Override
	public NativeResult getTotalResult(){
		QueryParameter param = createQueryParameter();
		QueryResult result = facade.executeTotalQuery(param, cs.getConnection());
		return new NativeResult(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult(){
		QueryParameter param = createQueryParameter();
		return facade.executeFetch(param, cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#count()
	 */
	@Override
	public int count(){
		QueryParameter parameter = createParameter(new QueryParameter());
		parameter.setFirstResult(firstResult);
		parameter.setMaxSize(maxSize);
		return facade.executeCount(parameter, cs.getConnection());
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		QueryParameter param = createQueryParameter();
		return facade.executeQuery(param, cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		setMaxResults(1);
		List<T> result = getResultList();
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		UpdateParameter parameter = createParameter(new UpdateParameter());
		return facade.executeUpdate(parameter, cs.getConnection());
	}
	
	/**
	 * @return the parameter
	 */
	private QueryParameter createQueryParameter(){
		QueryParameter parameter = createParameter(new QueryParameter());		
		parameter.setMaxSize(maxSize);
		parameter.setFirstResult(firstResult);
		parameter.setResultType(resultType);
		
		if(hints.containsKey(PersistenceHints.SQLENGINE_JDBC_FETCHSIZE)){
			parameter.setFetchSize((Integer)hints.get(PersistenceHints.SQLENGINE_JDBC_FETCHSIZE));
		}
		
		if(filter != null){
			parameter.setFilter(new DelegatingResultSetFilter(filter));
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

		if(hints.containsKey(PersistenceHints.SQLENGINE_JDBC_TIMEOUT)){
			parameter.setTimeoutSeconds((Integer)hints.get(PersistenceHints.SQLENGINE_JDBC_TIMEOUT));
		}
		
		parameter.setAllParameter(param);
		parameter.setAllBranchParameter(branchParam);
		parameter.setUseRowSql(useRowSql);
		return parameter;
	}

}

/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.elink.free.strategy;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;

import alpha.jdbc.strategy.ConstantAccessor;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.impl.ConstantAccessorImpl;
import alpha.sqlclient.free.FreeModifyQueryParameter;
import alpha.sqlclient.free.FreeQueryParameter;
import alpha.sqlclient.free.FreeReadQueryParameter;
import alpha.sqlclient.free.HitData;
import alpha.sqlclient.free.strategy.InternalQuery;




/**
 * The internal named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNamedQueryImpl implements InternalQuery{
	
	/** the pattern */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the <code>QueryBuilder</code> */
	private QueryBuilder builder;
	
	/** the <code>ConstantAccessor</code> */
	private ConstantAccessor accessor = new ConstantAccessorImpl();
	
	/**
	 * @param builder the builder to set
	 */
	public void setQueryBuilder(QueryBuilder builder){
		this.builder = builder;
	}

	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstantAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#count(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@Override
	public long count(FreeReadQueryParameter param){
		throw new UnsupportedOperationException();
	}	
	
	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getResultList(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(FreeReadQueryParameter param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		return setRange(param.getFirstResult(),param.getMaxSize(),query).getResultList();
	}

	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getSingleResult(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(FreeReadQueryParameter param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		return (T)setRange(param.getFirstResult(),1,query).getSingleResult();
	}
	
	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#executeUpdate(alpha.sqlclient.elink.free.FreeModifyQueryParameter)
	 */
	@Override
	public int executeUpdate(FreeModifyQueryParameter param) {
		Query query = createQuery(param);
		query = mapping(param,query);
		return query.executeUpdate();
	}

	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	protected Query createQuery(FreeQueryParameter param) {
		String executingSql = param.getSql();
		Query query = null;
		//SQLエンジンを使用してクエリを読み込む
		if( param.getName() == null){				
			//解析未使用
			if( param.isUseRowSql() ){					
				query = param.getEntityManager().createQuery(executingSql);			
				for(Map.Entry<String, Object> p : param.getParam().entrySet()){
					query.setParameter(p.getKey(), p.getValue());				
				}
			}else{				
				executingSql = builder.build(param.getQueryId(), executingSql);
				executingSql = builder.evaluate(executingSql, param.getParam(),param.getQueryId());	
				query = setParameters(executingSql,param.getParam(),param.getEntityManager().createQuery(executingSql));	
			}
		//名前付きクエリ
		}else {
			query = setParameters(executingSql,param.getParam(), param.getEntityManager().createNamedQuery(param.getName()));
		}
		return query;
	}
	
	/**
	 * Set the parameters to the specified query.
	 * @param executingSql
	 * @param param
	 * @param query
	 * @return
	 */
	private Query setParameters(String executingSql,Map<String,Object> param,Query query){
		
		// バインド変数を検索
		Matcher match = BIND_VAR_PATTERN.matcher(executingSql);

		// バインド変数にマッチした部分を取得してパラメータ追加
		while (match.find()) {
			// マッチしたバインド変数名を取得(前後の空白、1文字目のコロンを除く)
			String variableName = match.group(2);
			Object[] value = accessor.getConstTarget(variableName);
			
			//定数
			if( value.length > 0 ){
				query.setParameter(variableName, value[0]);
			//定数以外
			}else{
				if(param.containsKey(variableName)){				
					query.setParameter(variableName, param.get(variableName));						
				}
			}
		}
		return query;
	}
	
	/**
	 * @param query　the query
	 * @return the query
	 */
	protected Query mapping(FreeQueryParameter param,Query query){
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		return query;
	}
	
	/**
	 * Set the range.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query setRange(int firstResult, int maxSize,Query query){
		if( maxSize > 0){
			query.setMaxResults(maxSize);
		}
		if( firstResult > 0){
			query.setFirstResult(firstResult);
		}
		return query;
	}

	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getTotalResult(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@Override
	public HitData getTotalResult(FreeReadQueryParameter param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getFetchResult(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeReadQueryParameter param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeModifyQueryParameter> param) {
		throw new UnsupportedOperationException();
	}

}

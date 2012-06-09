/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.free.strategy;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.sqlclient.free.FreeQueryParameter;
import kosmos.framework.sqlclient.free.FreeSelectParameter;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.NativeResult;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.impl.ConstAccessorImpl;


/**
 * The internal named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNamedQueryImpl implements InternalQuery{
	
	/** the pattern */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the EntityManager */
	private EntityManager em;

	/** the <code>SQLBuilder</code> */
	private SQLBuilder builder;
	
	/** the <code>ConstAccessor</code> */
	private ConstAccessor accessor = new ConstAccessorImpl();

	/**
	 * @param em the em to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider em){
		this.em = em.getEntityManager();
	}
	
	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}

	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#count(kosmos.framework.sqlclient.free.FreeSelectParameter)
	 */
	@Override
	public long count(FreeSelectParameter param){
		throw new UnsupportedOperationException();
	}	
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getResultList(kosmos.framework.sqlclient.free.FreeSelectParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(FreeSelectParameter param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		return setRange(param.getFirstResult(),param.getMaxSize(),query).getResultList();
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getSingleResult(kosmos.framework.sqlclient.free.FreeSelectParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(FreeSelectParameter param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		return (T)setRange(param.getFirstResult(),1,query).getSingleResult();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#executeUpdate(kosmos.framework.sqlclient.free.FreeUpdateParameter)
	 */
	@Override
	public int executeUpdate(FreeUpdateParameter param) {
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
				query = em.createQuery(executingSql);			
				for(Map.Entry<String, Object> p : param.getParam().entrySet()){
					query.setParameter(p.getKey(), p.getValue());				
				}
			}else{				
				executingSql = builder.build(param.getQueryId(), executingSql);
				executingSql = builder.evaluate(executingSql, param.getBranchParam(),param.getQueryId());	
				query = setParameters(executingSql,param.getParam(),em.createQuery(executingSql));	
			}
		//名前付きクエリ
		}else {
			query = setParameters(executingSql,param.getParam(), em.createNamedQuery(param.getName()));
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
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getTotalResult(kosmos.framework.sqlclient.free.FreeSelectParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeSelectParameter param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getFetchResult(kosmos.framework.sqlclient.free.FreeSelectParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeSelectParameter param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeUpdateParameter> param) {
		throw new UnsupportedOperationException();
	}

}

/**
 * Copyright 2011 the original author
 */
package alpha.query.elink.free.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import alpha.jdbc.strategy.ConstantAccessor;
import alpha.jdbc.strategy.QueryLoader;
import alpha.jdbc.strategy.impl.ConstantAccessorImpl;
import alpha.query.free.Conditions;
import alpha.query.free.HitData;
import alpha.query.free.ModifyingConditions;
import alpha.query.free.ReadingConditions;
import alpha.query.free.gateway.PersistenceGateway;




/**
 * The internal named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class EclipseLinkJpqlGateway implements PersistenceGateway{
	
	/** the pattern */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the <code>QueryLoader</code> */
	private QueryLoader loader;
	
	/** the <code>ConstantAccessor</code> */
	private ConstantAccessor accessor = new ConstantAccessorImpl();
	
	/**
	 * @param loader the loader to set
	 */
	public void setQueryLoader(QueryLoader loader){
		this.loader = loader;
	}

	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstantAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#count(alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public long count(ReadingConditions param){
		throw new UnsupportedOperationException();
	}	
	
	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#getResultList(alpha.query.elink.free.ReadingConditions)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(ReadingConditions param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		List<T> resultList = setRange(param.getFirstResult(),param.getMaxSize(),query).getResultList();		
		if(param.getFilter() != null){
			List<T> edited = new ArrayList<T>();
			for(T res : resultList){
				edited.add(param.getFilter().edit(res));
			}
			return edited;
		}else {
			return resultList;
		}
	}

	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#getSingleResult(alpha.query.elink.free.ReadingConditions)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(ReadingConditions param){
		Query query = createQuery(param);
		query = mapping( param , query );
		if(param.getLockMode() != null){
			query.setLockMode(param.getLockMode());
		}
		try{
			T result =  (T)setRange(param.getFirstResult(),1,query).getSingleResult();
			if(param.getFilter() != null && result != null){
				result = param.getFilter().edit(result);
			}
			return result;
		}catch(NoResultException nre){
			return null;
		}
		
	}
	
	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#executeUpdate(alpha.query.elink.free.ModifyingConditions)
	 */
	@Override
	public int executeUpdate(ModifyingConditions param) {
		Query query = createQuery(param);
		query = mapping(param,query);
		return query.executeUpdate();
	}

	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	protected Query createQuery(Conditions param) {
		String executingSql = param.getSql();
		Query query = null;
	
		//解析未使用
		if( param.isUseRowSql() ){					
			query = param.getEntityManager().createQuery(executingSql);			
			for(Map.Entry<String, Object> p : param.getParam().entrySet()){
				query.setParameter(p.getKey(), convert(p.getValue()));				
			}
		}else{				
			executingSql = loader.build(param.getQueryId(), executingSql);
			executingSql = loader.evaluate(executingSql, param.getParam(),param.getQueryId());	
			query = setParameters(executingSql,param.getParam(),param.getEntityManager().createQuery(executingSql));	
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
			//定数
			if( accessor.isValidKey(variableName) ){
				query.setParameter(variableName, convert(accessor.getConstTarget(variableName)));
			//定数以外
			}else{
				if(param.containsKey(variableName)){				
					query.setParameter(variableName, convert(param.get(variableName)));						
				}
			}
		}
		return query;
	}
	
	/**
	 * Convert the value
	 * @param value the value to add
	 * @return the converted value
	 */
	protected Object convert(Object value){

		return value;
	}
	
	/**
	 * @param query　the query
	 * @return the query
	 */
	protected Query mapping(Conditions param,Query query){
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
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#getTotalResult(alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public HitData getTotalResult(ReadingConditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#getFetchResult(alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getFetchResult(ReadingConditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see alpha.query.elink.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<ModifyingConditions> param) {
		throw new UnsupportedOperationException();
	}

}

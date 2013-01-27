/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.elink.free.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.coder.alpha.jdbc.strategy.ConstantAccessor;
import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.impl.ConstantAccessorImpl;
import org.coder.alpha.query.free.Conditions;
import org.coder.alpha.query.free.HitData;
import org.coder.alpha.query.free.ModifyingConditions;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.gateway.PersistenceGateway;



/**
 * The EclipseLinkJpqlGateway.
 *
 * @author yoshida-n
 * @version created.
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
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#count(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public long count(ReadingConditions param){
		throw new UnsupportedOperationException();
	}	
	
	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#getResultList(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(ReadingConditions param){
		Query query = setRangeAndLockMode(param.getLockMode(),param.getFirstResult(),param.getMaxResults(),createQuery(param));	
		List<T> resultList = query.getResultList();		
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
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#executeUpdate(org.coder.alpha.query.elink.free.ModifyingConditions)
	 */
	@Override
	public int executeUpdate(ModifyingConditions param) {
		Query query = createQuery(param);
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
			query = param.getEntityManager().createQuery(executingSql);	

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
					if(param.getParam().containsKey(variableName)){				
						query.setParameter(variableName, convert(param.getParam().get(variableName)));						
					}
				}
			}
			
		}
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
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
	 * Set the range.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query setRangeAndLockMode(LockModeType lock,int firstResult, int maxSize,Query query){
		if( maxSize > 0){
			query.setMaxResults(maxSize);
		}
		if( firstResult > 0){
			query.setFirstResult(firstResult);
		}
		if(lock != null){
			query.setLockMode(lock);
		}
		return query;
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#getTotalResult(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public <T> HitData<T> getTotalResult(ReadingConditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#getFetchResult(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getFetchResult(ReadingConditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<ModifyingConditions> param) {
		throw new UnsupportedOperationException();
	}

}

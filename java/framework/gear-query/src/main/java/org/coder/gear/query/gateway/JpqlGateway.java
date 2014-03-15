/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.gateway;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.coder.gear.query.free.loader.ConstantAccessible;
import org.coder.gear.query.free.loader.QueryLoader;
import org.coder.gear.query.free.loader.QueryLoaderTrace;
import org.coder.gear.query.free.query.Conditions;



/**
 * The EclipseLinkJpqlGateway.
 *
 * @author yoshida-n
 * @version created.
 */
public class JpqlGateway implements PersistenceGateway ,ConstantAccessible{
	
	/** the pattern */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the <code>QueryLoader</code> */
	private QueryLoader loader = new QueryLoaderTrace();
		
	/** the em */
	private EntityManager em;
	
	/**
	 * @param em to set
	 */
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * @param loader the loader to set
	 */
	public void setQueryLoader(QueryLoader loader){
		this.loader = loader;
	}
	
	/**
	 * @see org.coder.alpha.query.gateway.elink.free.gateway.PersistenceGateway#getResultList(org.coder.alpha.query.free.query.elink.free.ReadingConditions)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(Conditions param){
		Query query = setRangeAndLockMode(param.getLockMode(),param.getFirstResult(),param.getMaxResults(),createQuery(param));	
		List<T> resultList = query.getResultList();		
		return resultList;		
	}
	
	/**
	 * @see org.coder.alpha.query.gateway.elink.free.gateway.PersistenceGateway#executeUpdate(org.coder.alpha.query.elink.free.ModifyingConditions)
	 */
	@Override
	public int executeUpdate(Conditions param) {
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
			query = em.createQuery(executingSql);			
			for(Map.Entry<String, Object> p : param.getParam().entrySet()){
				query.setParameter(p.getKey(), convert(p.getValue()));				
			}
		}else{				
			executingSql = loader.build(param.getQueryId(), executingSql);
			executingSql = loader.evaluate(executingSql, param.getParam(),param.getQueryId());	
			query = em.createQuery(executingSql);	

			// バインド変数を検索
			Matcher match = BIND_VAR_PATTERN.matcher(executingSql);
			// バインド変数にマッチした部分を取得してパラメータ追加
			while (match.find()) {
				// マッチしたバインド変数名を取得(前後の空白、1文字目のコロンを除く)
				String variableName = match.group(2);
				//定数
				if( isValidKey(variableName) ){
					query.setParameter(variableName, convert(getConstTarget(variableName)));
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

}

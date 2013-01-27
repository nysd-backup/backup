/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.strategy.QueryLoader;



/**
 * SQLをキャッシュに保存する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryLoaderProxyImpl implements QueryLoader{
	
	/** SQLキャッシュ. */
	private static final Map<String, String> CACHE = new ConcurrentHashMap<String, String>();

	/** デリゲート. */
	private QueryLoader delegate = new QueryLoaderImpl();
	
	/** キャッシュ使用有無. */
	private boolean useCache = true;
	
	/**
	 * @param delegate デリゲート
	 */
	public void setDelegate(QueryLoader delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @param useCache キャッシュ使用有無
	 */
	public void setUseCache(boolean useCache){
		this.useCache = useCache;
	}
	
	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#build(java.lang.String, java.lang.String)
	 */
	@Override
	public String build(String queryId, String rowString) {

		//キャッシュモード有効かつ、ファイル読み取りSQLの場合キャッシュに保持する
		if( useCache && Pattern.compile("^@(.+)").matcher(rowString).find()){
			if(CACHE.containsKey(queryId)){
				return CACHE.get(queryId);
				
			}else{
				String structed = delegate.build(queryId, rowString);
				CACHE.put(queryId, structed);
				return structed;
			}
		}else{
			return delegate.build(queryId, rowString);
		}
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	@Override
	public String evaluate(String query, Map<String, Object> parameter,String sqlId) {
		return delegate.evaluate(query, parameter,sqlId);
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String originalSql, List<Map<String, Object>> parameter,String wrapClause,String queryId) {
		return delegate.prepare(originalSql, parameter, wrapClause,queryId);
	}

}
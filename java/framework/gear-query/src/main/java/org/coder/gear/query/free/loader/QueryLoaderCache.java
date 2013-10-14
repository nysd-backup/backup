/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


/**
 * Cache for Query.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class QueryLoaderCache implements QueryLoader{
	
	/** cache. */
	private static final Map<String, String> CACHE = new ConcurrentHashMap<String, String>();

	/** delegate */
	private QueryLoader delegate = new DefaultQueryLoader();
	
	/** enable cache. */
	private boolean useCache = true;
	
	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(QueryLoader delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @param useCache the useCache to set
	 */
	public void setUseCache(boolean useCache){
		this.useCache = useCache;
	}
	
	/**
	 * @see org.coder.gear.query.free.loader.QueryLoader#build(java.lang.String, java.lang.String)
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
	 * @see org.coder.gear.query.free.loader.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	@Override
	public String evaluate(String query, Map<String, Object> parameter,String sqlId) {
		return delegate.evaluate(query, parameter,sqlId);
	}

	/**
	 * @see org.coder.gear.query.free.loader.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String originalSql, Map<String, Object> parameter,String wrapClause,String queryId) {
		return delegate.prepare(originalSql, parameter, wrapClause,queryId);
	}

}

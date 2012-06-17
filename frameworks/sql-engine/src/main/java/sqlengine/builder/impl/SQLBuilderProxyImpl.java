/**
 * Copyright 2011 the original author
 */
package sqlengine.builder.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import sqlengine.builder.SQLBuilder;



/**
 * SQLをキャッシュに保存する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLBuilderProxyImpl implements SQLBuilder{
	
	/** SQLキャッシュ. */
	private static final Map<String, String> CACHE = new ConcurrentHashMap<String, String>();

	/** デリゲート. */
	private SQLBuilder delegate = new SQLBuilderImpl();
	
	/** キャッシュ使用有無. */
	private boolean useCache = true;
	
	/**
	 * @param delegate デリゲート
	 */
	public void setDelegate(SQLBuilder delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @param useCache キャッシュ使用有無
	 */
	public void setUseCache(boolean useCache){
		this.useCache = useCache;
	}
	
	/**
	 * @see sqlengine.builder.SQLBuilder#build(java.lang.String, java.lang.String)
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
	 * @see sqlengine.builder.SQLBuilder#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	@Override
	public String evaluate(String query, Map<String, Object> parameter,String sqlId) {
		return delegate.evaluate(query, parameter,sqlId);
	}

	/**
	 * @see sqlengine.builder.SQLBuilder#replaceToPreparedSql(java.lang.String, java.util.Map, java.util.List, java.lang.String)
	 */
	@Override
	public String replaceToPreparedSql(String originalSql, List<Map<String, Object>> parameter,
			List<List<Object>> bindList, String queryId) {
		return delegate.replaceToPreparedSql(originalSql, parameter, bindList, queryId);
	}

	/**
	 * @see sqlengine.builder.SQLBuilder#setCount(java.lang.String)
	 */
	@Override
	public String setCount(String sql) {
		return delegate.setCount(sql);
	}

}

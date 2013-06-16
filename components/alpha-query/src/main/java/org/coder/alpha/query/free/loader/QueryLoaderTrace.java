/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Output query statement log.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryLoaderTrace implements QueryLoader{
	
	private static final Log LOG = LogFactory.getLog("QUERY." +QueryLoaderTrace.class);
	
	/** delegate */
	private QueryLoader delegate = new QueryLoaderCache();
	
	/** the list contains query id */
	private List<String> ignoreList = new ArrayList<String>();
	
	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(QueryLoader delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @param ignoreList the ignoreList to set
	 */
	public void setIgnoreList(String ignoreList){
		String[] ignore = ignoreList.split(",");
		this.ignoreList = Arrays.asList(ignore);
	}

	/**
	 * @see org.coder.alpha.query.free.loader.QueryLoader#build(java.lang.String, java.lang.String)
	 */
	@Override
	public String build(String queryId, String rowString) {
		return delegate.build(queryId, rowString);
	}

	/**
	 * @see org.coder.alpha.query.free.loader.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	@Override
	public String evaluate(String query, Map<String, Object> parameter,
			String queryId) {
		if(LOG.isTraceEnabled() && !ignoreList.contains(queryId)){						
			StringBuilder builder = new StringBuilder();
			for(Map.Entry<String, Object> v : parameter.entrySet()){						
				if(v.getValue() instanceof String){
					builder.append(v.getKey()).append("=\'").append(v.getValue()).append("\' ");	
				}else{
					builder.append(v.getKey()).append("=").append(v.getValue()).append(" ");
				}
			}
			LOG.trace(String.format("sql before prepared statement \n%s\n[%s]",query,builder.toString()));				
		}
		//変換後ログ
		String result = delegate.evaluate(query, parameter, queryId);
		if(LOG.isTraceEnabled() && !ignoreList.contains(queryId)){		
			String replaced = String.class.cast(result);	
			LOG.trace(String.format("sql after evaluate \n%s\n",replaced));				
		}
		return result;
	}

	/**
	 * @see org.coder.alpha.query.free.loader.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String originalSql,
			Map<String, Object> parameter, String wrapClause,
			String queryId) {
		if(ignoreList.contains(queryId)){
			return delegate.prepare(originalSql, parameter, wrapClause, queryId);
		}else{		
			PreparedQuery value = delegate.prepare(originalSql, parameter, wrapClause, queryId);			
			if(LOG.isDebugEnabled()){
				StringBuilder builder = new StringBuilder();				
				builder.append("[");
				boolean first = true;
				for(Object o : value.getBindList()){
					if(first){
						first = false;
					}else{
						builder.append(",");
					}
					builder.append((o instanceof String) ? "\'" + o  + "\'" : o);
				}
				builder.append("]\n");						
				LOG.debug(String.format("executing sql = \n%s\n%s",value.getQueryStatement(),builder.toString()));		
			}
			if(LOG.isInfoEnabled()){
				StringBuilder convertedQuery = new StringBuilder();				
				Iterator<Object> ite = value.getBindList().iterator();
				String converted = value.getQueryStatement();
				
				//?にパラメータを埋め込む
				while(converted.contains("?")){
					if( !ite.hasNext() ){
						throw new IllegalStateException("count of ? is different from parameter count");
					}
					Object v = ite.next();
					converted = StringUtils.replaceOnce(converted, "?", (v instanceof String) ? "\'" + v  + "\'" : String.valueOf(v));		
				}	
				convertedQuery.append(String.format("complete sql = \n%s",converted));
			
				LOG.info(convertedQuery.toString());
			}
			return value;			
		}
	}

}

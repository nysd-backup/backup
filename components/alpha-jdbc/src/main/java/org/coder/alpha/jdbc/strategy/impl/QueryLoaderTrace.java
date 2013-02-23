/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.strategy.QueryLoader;

/**
 * Output query statement log.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryLoaderTrace implements QueryLoader{
	
	private static final Log LOG = LogFactory.getLog("QUERY." +QueryLoaderTrace.class);
	
	/** delegate */
	private QueryLoader delegate = new QueryLoaderProxy();
	
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
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#build(java.lang.String, java.lang.String)
	 */
	@Override
	public String build(String queryId, String rowString) {
		return delegate.build(queryId, rowString);
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
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
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String originalSql,
			List<Map<String, Object>> parameter, String wrapClause,
			String queryId) {
		if(ignoreList.contains(queryId)){
			return delegate.prepare(originalSql, parameter, wrapClause, queryId);
		}else{
			PreparedQuery value = delegate.prepare(originalSql, parameter, wrapClause, queryId);
			if(LOG.isInfoEnabled()){
				String query = value.toString();
				String[] splited = query.split(";");				
				LOG.info(splited[0]);
				LOG.debug(splited[1]);
			}
			return value;			
		}
	}

}

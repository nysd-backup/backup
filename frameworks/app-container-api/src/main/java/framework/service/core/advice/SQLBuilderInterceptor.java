/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;

/**
 * An advice for the SQL Builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLBuilderInterceptor implements Advice{

	private static final LogWriter LOG = LogWriterFactory.getLog(SQLBuilderInterceptor.class);
	
	/** the list contains query id */
	private List<String> ignoreList = new ArrayList<String>();
	
	/**
	 * @param ignoreList the ignoreList to set
	 */
	public void setIgnoreList(String ignoreList){
		String[] ignore = ignoreList.split(",");
		this.ignoreList = Arrays.asList(ignore);
	}

	/**
	 * @see framework.service.core.advice.Advice#before(java.lang.Object, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void before(Object target, String method, Object[] argments) {				
		if(LOG.isDebugEnabled() && !ignoreList.contains(argments[2])){
			String previous = String.class.cast(argments[0]);			
			Map<String, Object> parameter = (Map<String, Object>)argments[1];				
			StringBuilder builder = new StringBuilder();
			for(Map.Entry<String, Object> v : parameter.entrySet()){						
				if(v.getValue() instanceof String){
					builder.append(v.getKey()).append("=\'").append(v.getValue()).append("\' ");	
				}else{
					builder.append(v.getKey()).append("=").append(v.getValue()).append(" ");
				}
			}
			LOG.debug(String.format("sql before prepared statement \r\n%s\r\n[%s]",previous,builder.toString()));			
		}				
	}

	/**
	 * @see framework.service.core.advice.Advice#after(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void after(Object target, String method, Object[] argments,Object result) {
		String replaced = String.class.cast(result);	
		if(!ignoreList.contains(argments[2])){
			LOG.info(String.format("sql after evaluate \r\n%s\r\n",replaced));				
		}
	}

}

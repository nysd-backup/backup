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
public class InternalSQLBuilderInterceptor implements InternalInterceptor{

	private static final LogWriter LOG = LogWriterFactory.getLog(InternalSQLBuilderInterceptor.class);
	
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
	 * @see framework.service.core.advice.InternalInterceptor#around(framework.service.core.advice.ContextAdapter)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object around(ContextAdapter contextInvoker) throws Throwable {				
		if(LOG.isDebugEnabled() && !ignoreList.contains(contextInvoker.getArgs()[2])){						
			Map<String,Object> parameter = (Map<String,Object>)(contextInvoker.getArgs()[1]);
			String previous = String.class.cast(contextInvoker.getArgs()[0]);
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
		//変換後ログ
		Object result = contextInvoker.proceed();
		if(!ignoreList.contains(contextInvoker.getArgs()[2])){
			String replaced = String.class.cast(result);	
			LOG.info(String.format("sql after evaluate \r\n%s\r\n",replaced));				
		}
		return result;
	}

}

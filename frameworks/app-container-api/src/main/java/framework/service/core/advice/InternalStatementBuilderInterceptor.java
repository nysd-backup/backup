/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;
import framework.service.core.utils.QueryUtils;

/**
 * An advice for StatementBuilder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalStatementBuilderInterceptor implements InternalInterceptor{

	private static final LogWriter LOG = LogWriterFactory.getLog(InternalStatementBuilderInterceptor.class);

	/** the list contains queryId */
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
	@SuppressWarnings("unchecked")
	@Override
	public Object around(ContextAdapter contextInvoker) throws Throwable {
		
		if(ignoreList.contains(contextInvoker.getArgs()[3])){
			return contextInvoker.proceed();
		}else{
			String sql = String.class.cast(contextInvoker.getArgs()[1]); 		
			List<Object> bindList = (List<Object>)contextInvoker.getArgs()[2];
			String converted = QueryUtils.applyValues(bindList, sql);
			LOG.info(String.format("sql for prepared statement %s%s","\r\n",converted));	
			
			return contextInvoker.proceed();
		}
	}

}

/**
 * Copyright 2011 the original author
 */
package service.framework.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.logics.log.LogWriter;
import core.logics.log.LogWriterFactory;



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
	 * @see service.framework.core.advice.InternalInterceptor#around(service.framework.core.advice.InvocationAdapter)
	 */
	@Override
	public Object around(InvocationAdapter contextInvoker) throws Throwable {
		
		if(ignoreList.contains(contextInvoker.getArgs()[0])){
			return contextInvoker.proceed();
		}else{
			Object value = contextInvoker.proceed();
			String query = value.toString();
			String[] splited = query.split(";");				
			LOG.info(splited[0]);
			LOG.debug(splited[1]);
			return value;			
		}
	}

}

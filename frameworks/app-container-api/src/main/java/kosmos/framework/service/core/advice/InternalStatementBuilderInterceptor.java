/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kosmos.framework.logics.log.LogWriter;
import kosmos.framework.logics.log.LogWriterFactory;
import kosmos.framework.service.core.utils.QueryUtils;


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
	 * @see kosmos.framework.service.core.advice.InternalInterceptor#around(kosmos.framework.service.core.advice.InvocationAdapter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object around(InvocationAdapter contextInvoker) throws Throwable {
		
		if(ignoreList.contains(contextInvoker.getArgs()[0])){
			return contextInvoker.proceed();
		}else{
			if(contextInvoker.getArgs().length > 3 ){
				String sql = String.class.cast(contextInvoker.getArgs()[2]); 		
				List<Object> bindList = (List<Object>)contextInvoker.getArgs()[3];
				String converted = QueryUtils.applyValues(bindList, sql);
				LOG.info(String.format("sql for prepared statement %s%s","\r\n",converted));	
			}
			return contextInvoker.proceed();
		}
	}

}

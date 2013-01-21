/**
 * Copyright 2011 the original author
 */
package alpha.framework.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alpha.framework.logging.DefaultQueryLoggerImpl;
import alpha.framework.logging.QueryLogger;



/**
 * An advice for StatementBuilder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalStatementBuilderInterceptor implements InternalInterceptor{

	/** the instance of logging */
	private QueryLogger logger = new DefaultQueryLoggerImpl();
	
	/** the list contains query id */
	private List<String> ignoreList = new ArrayList<String>();
	
	/**
	 * @param logger the logger to set
	 */
	public void setQueryLogger(QueryLogger logger){
		this.logger = logger;
	}
	
	/**
	 * @return enabled
	 */
	public boolean isEnabled(){
		return logger.isDebugEnabled();
	}
	
	/**
	 * @param ignoreList the ignoreList to set
	 */
	public void setIgnoreList(String ignoreList){
		String[] ignore = ignoreList.split(",");
		this.ignoreList = Arrays.asList(ignore);
	}

	/**
	 * @see alpha.framework.advice.InternalInterceptor#around(alpha.framework.advice.InvocationAdapter)
	 */
	@Override
	public Object around(InvocationAdapter contextInvoker) throws Throwable {
		
		if(ignoreList.contains(contextInvoker.getArgs()[0])){
			return contextInvoker.proceed();
		}else{
			Object value = contextInvoker.proceed();
			if(logger.isInfoEnabled()){
				String query = value.toString();
				String[] splited = query.split(";");				
				logger.info(splited[0]);
				logger.debug(splited[1]);
			}
			return value;			
		}
	}

}

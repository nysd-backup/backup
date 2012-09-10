/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import alpha.utility.LogUtils;




/**
 * An advice for StatementBuilder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalStatementBuilderInterceptor implements InternalInterceptor{

	private static final Logger LOG = Logger.getLogger(LogUtils.DEBUG + InternalStatementBuilderInterceptor.class.getName());

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
	 * @see alpha.framework.domain.advice.InternalInterceptor#around(alpha.framework.domain.advice.InvocationAdapter)
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

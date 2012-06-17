/**
 * Copyright 2011 the original author
 */
package service.framework.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.logics.log.LogWriter;
import core.logics.log.LogWriterFactory;
import core.logics.utility.QueryUtils;



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
	@SuppressWarnings("unchecked")
	@Override
	public Object around(InvocationAdapter contextInvoker) throws Throwable {
		
		if(ignoreList.contains(contextInvoker.getArgs()[0])){
			return contextInvoker.proceed();
		}else{
			if(contextInvoker.getArgs().length == 4 ){		
				Object value = contextInvoker.proceed();
				List<List<Object>> bindList = (List<List<Object>>)contextInvoker.getArgs()[2];
				StringBuilder builder = new StringBuilder();
				for(List<Object> e : bindList){					
					builder.append("[");
					boolean first = true;
					for(Object o : e){
						if(first){
							first = false;
						}else{
							builder.append(",");
						}
						builder.append(QueryUtils.getDisplayValue(o));
					}
					builder.append("]\n");			
				}
				LOG.info(String.format("executing sql = \n%s\n%s",value,builder.toString()));		
				if(LOG.isDebugEnabled()){
					for(List<Object> e : bindList){	
						String converted = QueryUtils.applyValues(e, (String)value);
						LOG.debug(String.format("complete sql = \n%s",converted));	
					}
				}
				return value;
			}else{
				return contextInvoker.proceed();
			}
		}
	}

}

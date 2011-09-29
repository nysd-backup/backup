/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.logics.log.LogWriterFactory;
import framework.logics.log.NormalLogWriter;
import framework.service.core.transaction.ServiceContext;


/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class PerfInterceptor {

	/** the instance of logging */
	private static final NormalLogWriter LOG = LogWriterFactory.getPerfLog(PerfInterceptor.class);
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable any exception
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Throwable {
		if(LOG.isDebugEnabled()){
			ServiceContext context = ServiceContext.getCurrentInstance();
			context.pushCallStack();
	
			long start = System.currentTimeMillis();
			try {
				// 処理実行
				return ic.proceed();
	
			} finally {
				long end = System.currentTimeMillis() - start;
	
				// パフォーマンスログの出力
				StringBuilder builder = new StringBuilder();
				for (int i = 1; i < context.getCallStackLevel(); i++) {
					builder.append("\t");
				}
				LOG.debug(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getMethod().getDeclaringClass().getName(), ic.getMethod().getName()));
	
				context.popCallStack();
			}
		}else{
			return ic.proceed();
		}
	}
}

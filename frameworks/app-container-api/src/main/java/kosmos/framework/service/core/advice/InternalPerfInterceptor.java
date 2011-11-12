/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.logics.log.LogWriterFactory;
import kosmos.framework.logics.log.NormalLogWriter;
import kosmos.framework.service.core.transaction.ServiceContext;

/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalPerfInterceptor implements InternalInterceptor{

	/** the instance of logging */
	private static final NormalLogWriter LOG = LogWriterFactory.getPerfLog(InternalPerfInterceptor.class);
	
	/**
	 * @see kosmos.framework.service.core.advice.InternalInterceptor#around(kosmos.framework.service.core.advice.InvocationAdapter)
	 */
	public Object around(InvocationAdapter ic) throws Throwable {
		
		if(LOG.isDebugEnabled()){
			ServiceContext context = ServiceContext.getCurrentInstance();
			if(context == null){
				return ic.proceed();
			}else{
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
					LOG.debug(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getDeclaringTypeName(), ic.getMethodName()));
		
					context.popCallStack();
				}
			}
		}else{
			return ic.proceed();
		}
	}
}

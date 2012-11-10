/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.advice;

import alpha.framework.domain.logging.DefaultPerfLoggerImpl;
import alpha.framework.domain.logging.PerfLogger;
import alpha.framework.domain.transaction.DomainContext;

/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalPerfInterceptor implements InternalInterceptor{

	/** the instance of logging */
	private PerfLogger logger = new DefaultPerfLoggerImpl();

	/**
	 * @return enabled
	 */
	public boolean isEnabled(){
		return logger.isInfoEnabled();
	}
	
	/**
	 * @see alpha.framework.domain.advice.InternalInterceptor#around(alpha.framework.domain.advice.InvocationAdapter)
	 */
	public Object around(InvocationAdapter ic) throws Throwable {

		//性能
		if(logger.isInfoEnabled()){
			DomainContext context = DomainContext.getCurrentInstance();
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
					logger.info(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getDeclaringTypeName(), ic.getMethodName()));
		
					context.popCallStack();
				}
			}
		}else{
			return ic.proceed();
		}
	}
}

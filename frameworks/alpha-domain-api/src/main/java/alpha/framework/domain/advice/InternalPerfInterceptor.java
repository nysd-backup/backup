/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.advice;

import org.apache.log4j.Logger;

import alpha.framework.domain.transaction.DomainContext;

/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalPerfInterceptor implements InternalInterceptor{

	/** the instance of logging */
	private static final Logger PERFLOG = Logger.getLogger("PERF." +InternalPerfInterceptor.class.getName());

	/**
	 * @return enabled
	 */
	public static boolean isEnabled(){
		return PERFLOG.isInfoEnabled();
	}
	
	/**
	 * @see alpha.framework.domain.advice.InternalInterceptor#around(alpha.framework.domain.advice.InvocationAdapter)
	 */
	public Object around(InvocationAdapter ic) throws Throwable {

		//性能
		if(PERFLOG.isInfoEnabled()){
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
					PERFLOG.info(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getDeclaringTypeName(), ic.getMethodName()));
		
					context.popCallStack();
				}
			}
		}else{
			return ic.proceed();
		}
	}
}

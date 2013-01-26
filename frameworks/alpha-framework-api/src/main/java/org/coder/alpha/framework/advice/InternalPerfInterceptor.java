/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.advice;

import org.coder.alpha.framework.logging.DefaultPerfLoggerImpl;
import org.coder.alpha.framework.logging.PerfLogger;
import org.coder.alpha.framework.transaction.TransactionContext;

/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalPerfInterceptor implements InternalInterceptor{

	/** the instance of logging */
	protected PerfLogger logger = new DefaultPerfLoggerImpl();
	
	/**
	 * @param logger the logger to set
	 */
	public void setPerfLogger(PerfLogger logger){
		this.logger = logger;
	}

	/**
	 * @return enabled
	 */
	public boolean isEnabled(){
		return logger.isInfoEnabled();
	}
	
	/**
	 * @see org.coder.alpha.framework.advice.InternalInterceptor#around(org.coder.alpha.framework.advice.InvocationAdapter)
	 */
	public Object around(InvocationAdapter ic) throws Throwable {

		if(logger.isInfoEnabled()){
			TransactionContext context = TransactionContext.getCurrentInstance();
			context.pushCallStack();		
			long start = before(ic,context.getCallStackLevel());
			try {
				return ic.proceed();
	
			} finally {
				after(start,ic,context.getCallStackLevel());
				context.popCallStack();
			}
		}else{
			return ic.proceed();
		}
	}
	
	/**
	 * Before process.
	 * @param ic context
	 * @return start time
	 */
	protected long before(InvocationAdapter ic,int callStackLevel){
		long start = System.currentTimeMillis();
		return start;
	}
	
	/**
	 * After process
	 * @param start start time
	 * @param ic context
	 * @param callStackLevel position of the method stack
	 */
	protected void after(long start,InvocationAdapter ic, int callStackLevel){
		long end = System.currentTimeMillis() - start;
		
		// パフォーマンスログの出力
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < callStackLevel; i++) {
			builder.append("\t");
		}
		logger.info(String.format("msec %d:\t%s%s.%s", end, builder.toString(), ic.getDeclaringTypeName(), ic.getMethodName()));

	}
}

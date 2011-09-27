/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.service.core.advice.Advice;

/**
 * 汎用インターセプター、beforeとafterのみサポート.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingInterceptor{
	
	/** アドバイス */
	private Advice advice;
	
	/**
	 * @param advice アドバイス
	 */
	public void setAdvice(Advice advice){
		this.advice = advice;
	}

	/**
	 * @param invocation　起動情報
	 * @return 結果
	 * @throws Throwable 例外
	 */
	public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
		advice.before(invocation.getTarget(), invocation.getSignature().getName(),invocation.getArgs());
		
		Object value = invocation.proceed();
		
		advice.after(invocation.getThis(), invocation.getSignature().getName(),invocation.getArgs(),value);
		
		return value;
	}

}

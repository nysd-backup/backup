/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InternalDefaultInterceptor;
import kosmos.framework.service.core.advice.InvocationAdapterImpl;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultInterceptor {
	
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		InternalDefaultInterceptor internal = new InternalDefaultInterceptor(){
			
			//現在トランザクションが異常であればロールバックフラグを立てる。
			//エラーメッセージ追加時などにSessionContext.setRollbackOnly()とすると以降でSessionBeanが生成できないのでこのタイミングでのみ実施する。
			//ただし自律トランザクションに関してはTransactionManagingContextのロールバックフラグを使用しないこと。
			
			@Override
			protected void afterProceed(TransactionManagingContext context){
				if(context.getCurrentUnitOfWork().isRollbackOnly()){
					sessionContext.setRollbackOnly();
				}				
			}
		};
		return internal.around(new InvocationAdapterImpl(ic));
	}
}

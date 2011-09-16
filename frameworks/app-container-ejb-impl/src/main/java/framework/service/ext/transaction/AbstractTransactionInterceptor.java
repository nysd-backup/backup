/**
 * Use is subject to license terms.
 */
package framework.service.ext.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.service.core.transaction.ServiceContext;

/**
 * トランザクション境界における処理を行うインターセプター.
 * トランザクションIDの取得はEJBコンテナの実装に依存するので抽象化する。
 * 一番外側で実行するようにすること。
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractTransactionInterceptor {

	@Resource
	private SessionContext context;
	
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Throwable {
		
		ServiceContextImpl sc = (ServiceContextImpl)ServiceContext.getCurrentInstance();
		String currentTransactionId = getCurrentTransactionId(context);
		NamedInternalUnitOfWork upperTransaction = (NamedInternalUnitOfWork)sc.getCurrentUnitOfWork();
		boolean newTransaction = upperTransaction == null || !currentTransactionId.equals(upperTransaction.getTransactionId());

		//トランザクション境界の場合新規作業単位に移行
		if(newTransaction){
			sc.startUnitOfWork();
			NamedInternalUnitOfWork current = (NamedInternalUnitOfWork)sc.getCurrentUnitOfWork();
			current.setTransactionId(currentTransactionId);
		}
		try{											
			Object retVal = ic.proceed();			

			//トランザクション境界の場合、コンテナにロールバック通知
			if(newTransaction){				
				if( sc.getCurrentUnitOfWork().isRollbackOnly()){
					context.setRollbackOnly();
				}				
			}
			return retVal;
		}finally {
			//トランザクション境界の場合、上位の作業単位に戻す
			if(newTransaction){			
				sc.endUnitOfWork();
			}
		}
		
	}
	
	/**
	 * @param context コンテキスト
	 * @return　トランザクションID
	 */
	protected abstract String getCurrentTransactionId(SessionContext context);
}

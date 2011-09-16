/**
 * Use is subject to license terms.
 */
package framework.service.ext.transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

import framework.core.exception.application.BusinessException;
import framework.service.core.transaction.ServiceContext;
import framework.service.core.transaction.TransactionManagingContext;

/**
 * コンテキストのロールバック情報の利用したトランザクション管理を行う.
 * ServiceContextにエラーレベル以上のメッセージがある場合、transactionManagerのロールバックフラグを立てる。
 * トランザクション境界の場合はServiceContextのロールバックフラグをfalseに戻し、トランザクション内の業務エラーの影響が他トランザクションに影響ないようにする。
 * 一番外側で実行するようにすること。
 *
 * @author yoshida-n
 * @version	2011/05/15 created.
 */
public class ContextControllableTransactionInterceptor extends TransactionInterceptor  {

	private static final long serialVersionUID = 1L;

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		// Work out the target class: may be <code>null</code>.
		// The TransactionAttributeSource should be passed the target class
		// as well as the method, which may be from an interface.
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		// If the transaction attribute is null, the method is non-transactional.
		final TransactionAttribute txAttr =
				getTransactionAttributeSource().getTransactionAttribute(invocation.getMethod(), targetClass);
		final PlatformTransactionManager tm = determineTransactionManager(txAttr);
		final String joinpointIdentification = methodIdentification(invocation.getMethod());

		if (txAttr == null || !(tm instanceof CallbackPreferringPlatformTransactionManager)) {
			// Standard transaction demarcation with getTransaction and commit/rollback calls.
			TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);					
			
			//トランザクション境界の場合トランザクション開始（エラーメッセージの自動判定が不要であればこれ系の処理はいらない)
			if(txInfo.getTransactionStatus().isNewTransaction()){
				((TransactionManagingContext)ServiceContext.getCurrentInstance()).startUnitOfWork();
			}
			
			Object retVal = null;		
			boolean commitable = true;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceed();
			
				//後処理
				commitable = afterProceed(txInfo,retVal,invocation);
				
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);
				throw ex;
			}
			finally {
				
				if(txInfo.getTransactionStatus().isNewTransaction()){
					//トランザクション境界でセッションコンテキスト初期化				
					((TransactionManagingContext)ServiceContext.getCurrentInstance()).endUnitOfWork();
				}
				
				cleanupTransactionInfo(txInfo);
			}
			if(commitable){	
				commitTransactionAfterReturning(txInfo);
			}
			return retVal;
		}

		else {
			// It's a CallbackPreferringPlatformTransactionManager: pass a TransactionCallback in.
			try {
				Object result = ((CallbackPreferringPlatformTransactionManager) tm).execute(txAttr,
						new TransactionCallback<Object>() {
							public Object doInTransaction(TransactionStatus status) {
								TransactionInfo txInfo = prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
								try {
									return invocation.proceed();
								}
								catch (Throwable ex) {
									if (txAttr.rollbackOn(ex)) {
										// A RuntimeException: will lead to a rollback.
										if (ex instanceof RuntimeException) {
											throw (RuntimeException) ex;
										}
										else {
											throw new ThrowableHolderException(ex);
										}
									}
									else {
										// A normal return value: will lead to a commit.
										return new ThrowableHolder(ex);
									}
								}
								finally {
									cleanupTransactionInfo(txInfo);
								}
							}
						});

				// Check result: It might indicate a Throwable to rethrow.
				if (result instanceof ThrowableHolder) {
					throw ((ThrowableHolder) result).getThrowable();
				}
				else {
					return result;
				}
			}
			catch (ThrowableHolderException ex) {
				throw ex.getCause();
			}
		}
	}

	/**
	 * メソッド正常終了後
	 * 
	 * @param txInfo トランザクション状態
	 * @param retVal 戻り値
	 * @param invocation 実行情報
	 */
	protected boolean afterProceed(TransactionInfo txInfo,Object retVal , MethodInvocation invocation){
		
		//現在トランザクションでロールバックフラグが設定されている場合
		if(((TransactionManagingContext)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly()){			
			completeTransactionAfterThrowing(txInfo,new BusinessException("set rollback only in current transaction"));			
			return false;
		}
		return true;
		
	}


	//---------------------------------------------------------------------
	// Serialization support
	//---------------------------------------------------------------------

	private void writeObject(ObjectOutputStream oos) throws IOException {
		// Rely on default serialization, although this class itself doesn't carry state anyway...
		oos.defaultWriteObject();

		// Deserialize superclass fields.
		oos.writeObject(getTransactionManagerBeanName());
		oos.writeObject(getTransactionManager());
		oos.writeObject(getTransactionAttributeSource());
		oos.writeObject(getBeanFactory());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		// Rely on default serialization, although this class itself doesn't carry state anyway...
		ois.defaultReadObject();

		// Serialize all relevant superclass fields.
		// Superclass can't implement Serializable because it also serves as base class
		// for AspectJ aspects (which are not allowed to implement Serializable)!
		setTransactionManagerBeanName((String) ois.readObject());
		setTransactionManager((PlatformTransactionManager) ois.readObject());
		setTransactionAttributeSource((TransactionAttributeSource) ois.readObject());
		setBeanFactory((BeanFactory) ois.readObject());
	}


	/**
	 * Internal holder class for a Throwable, used as a return value
	 * from a TransactionCallback (to be subsequently unwrapped again).
	 */
	private static class ThrowableHolder {

		private final Throwable throwable;

		public ThrowableHolder(Throwable throwable) {
			this.throwable = throwable;
		}

		public final Throwable getThrowable() {
			return this.throwable;
		}
	}


	/**
	 * Internal holder class for a Throwable, used as a RuntimeException to be
	 * thrown from a TransactionCallback (and subsequently unwrapped again).
	 */
	private static class ThrowableHolderException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ThrowableHolderException(Throwable throwable) {
			super(throwable);
		}

		@Override
		public String toString() {
			return getCause().toString();
		}
	}
}

/**
 * Copyright 2011 the original author
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
 * 繧ｳ繝ｳ繝・く繧ｹ繝医・繝ｭ繝ｼ繝ｫ繝舌ャ繧ｯ諠・ｱ縺ｮ蛻ｩ逕ｨ縺励◆繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ邂｡逅・ｒ陦後≧.
 * 
 * <pre>
 * ServiceContext縺ｫ繧ｨ繝ｩ繝ｼ繝ｬ繝吶Ν莉･荳翫・繝｡繝・そ繝ｼ繧ｸ縺後≠繧句ｴ蜷医》ransactionManager縺ｮ繝ｭ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ繧堤ｫ九※繧九・
 * 繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｮ蝣ｴ蜷医・ServiceContext縺ｮ繝ｭ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ繧断alse縺ｫ謌ｻ縺励√ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蜀・・讌ｭ蜍吶お繝ｩ繝ｼ縺ｮ蠖ｱ髻ｿ縺御ｻ悶ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺ｫ蠖ｱ髻ｿ縺ｪ縺・ｈ縺・↓縺吶ｋ縲・
 * 荳逡ｪ螟門・縺ｧ螳溯｡後☆繧九ｈ縺・↓縺吶ｋ縺薙→縲・
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
			
			//繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｮ蝣ｴ蜷医ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ髢句ｧ具ｼ医お繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ縺ｮ閾ｪ蜍募愛螳壹′荳崎ｦ√〒縺ゅｌ縺ｰ縺薙ｌ邉ｻ縺ｮ蜃ｦ逅・・縺・ｉ縺ｪ縺・
			if(txInfo.getTransactionStatus().isNewTransaction()){
				((TransactionManagingContext)ServiceContext.getCurrentInstance()).startUnitOfWork();
			}
			
			Object retVal = null;		
			boolean commitable = true;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceed();
			
				//蠕悟・逅・
				commitable = afterProceed(txInfo,retVal,invocation);
				
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);
				throw ex;
			}
			finally {
				
				if(txInfo.getTransactionStatus().isNewTransaction()){
					//繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｧ繧ｻ繝・す繝ｧ繝ｳ繧ｳ繝ｳ繝・く繧ｹ繝亥・譛溷喧				
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
	 * 繝｡繧ｽ繝・ラ豁｣蟶ｸ邨ゆｺ・ｾ・
	 * 
	 * @param txInfo 繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ迥ｶ諷・
	 * @param retVal 謌ｻ繧雁､
	 * @param invocation 螳溯｡梧ュ蝣ｱ
	 */
	protected boolean afterProceed(TransactionInfo txInfo,Object retVal , MethodInvocation invocation){
		
		//迴ｾ蝨ｨ繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺ｧ繝ｭ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ縺瑚ｨｭ螳壹＆繧後※縺・ｋ蝣ｴ蜷・
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

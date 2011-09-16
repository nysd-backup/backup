/**
 * Use is subject to license terms.
 */
package framework.service.test;

import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.OptimisticLockException;

import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.AbstractJPAExceptionHandler;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DumyExceptionHandler extends AbstractJPAExceptionHandler{

	/**
	 * @see framework.service.ext.transaction.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handleOptimisticLockException(OptimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番チェックエラー無視");
		}else{
			throw e;
		}
		return null;
	}
	
	/**
	 * @see framework.service.ext.transaction.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handleDatabaseException(DatabaseException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("データベースエラー無視");
		}else{
			throw e;
		}
		return null;
	}
	
	/**
	 * @see framework.service.ext.transaction.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handlePessimisticLockException(PessimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("悲観ロックエラー無視");
		}else{
			throw e;
		}
		return null;
	}

}

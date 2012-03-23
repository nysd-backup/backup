/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import javax.persistence.PessimisticLockException;

import kosmos.framework.service.core.transaction.ServiceContext;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;
import org.eclipse.persistence.exceptions.OptimisticLockException;


/**
 * JPQLでBatchUpdateを実行するように設定している場合ExceptionHandlerは呼び出されない.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DumyExceptionHandler implements ExceptionHandler{

	protected Object handleOptimisticLockException(OptimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		
		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}
	
	protected Object handleDatabaseException(DatabaseException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}
	
	protected Object handlePessimisticLockException(PessimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		
		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}

	@Override
	public Object handleException(RuntimeException exception) {
		if(exception instanceof OptimisticLockException){
			return handleOptimisticLockException((OptimisticLockException)exception);
		}else if(exception instanceof PessimisticLockException){
			return handlePessimisticLockException((PessimisticLockException)exception);
		}else if(exception instanceof DatabaseException){
			return handleDatabaseException((DatabaseException)exception);
		}
		throw exception;
	}

}

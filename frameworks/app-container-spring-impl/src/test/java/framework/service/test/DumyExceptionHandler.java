/**
 * Copyright 2011 the original author
 */
package framework.service.test;

import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;
import org.eclipse.persistence.exceptions.OptimisticLockException;

import framework.service.core.transaction.ServiceContext;

/**
 * function.
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
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * Copyright 2011 the original author
 */
package service;


import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.OptimisticLockException;

import alpha.framework.transaction.TransactionContext;
import alpha.framework.transaction.autonomous.AutonomousTxContext;
import alpha.query.elink.customizer.JPQLExceptionHandlerImpl;



/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DumyExceptionHandler extends JPQLExceptionHandlerImpl{

	//TODO ここで判定するにはDat
	
	protected Object handleOptimisticLockException(OptimisticLockException e){
		AutonomousTxContext context = (AutonomousTxContext)TransactionContext.getCurrentInstance();

		if( "IGNORE_TEST".equals(context.getRequestId()) ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}

	@Override
	public Object handleException(RuntimeException exception) {
		//楽観ロック例外かDatabaseExceptionしかない
		if(exception instanceof OptimisticLockException){
			return handleOptimisticLockException((OptimisticLockException)exception);
		}else if(exception instanceof DatabaseException){
			try{
				return super.handleException(exception);
			}catch(RuntimeException uce){
				AutonomousTxContext context = (AutonomousTxContext)TransactionContext.getCurrentInstance();
				if( "IGNORE_TEST".equals(context.getRequestId()) ){
					System.out.println("ロック連番");
				}else{
					throw uce;
				}
				return null;
			}
		}
		throw exception;
	}

}

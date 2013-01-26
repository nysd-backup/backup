/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.Map;

import javax.persistence.PessimisticLockException;

import org.coder.alpha.framework.transaction.TransactionContext;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;
import org.eclipse.persistence.exceptions.OptimisticLockException;
import org.eclipse.persistence.internal.jpa.QueryHintsHandler;




/**
 * JPQLでBatchUpdateを実行するように設定している場合ExceptionHandlerは呼び出されない.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DumyExceptionHandler implements ExceptionHandler{

	@SuppressWarnings({"unchecked","unused"})
	protected Object handleOptimisticLockException(OptimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)TransactionContext.getCurrentInstance();
		
		Map<String,Object> hints = (Map<String,Object>)e.getQuery().getProperty(QueryHintsHandler.QUERY_HINT_PROPERTY);
		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}
	@SuppressWarnings({"unchecked","unused"})
	protected Object handleDatabaseException(DatabaseException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)TransactionContext.getCurrentInstance();
		Map<String,Object> hints = (Map<String,Object>)e.getQuery().getProperty(QueryHintsHandler.QUERY_HINT_PROPERTY);
		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}
	
	protected Object handlePessimisticLockException(PessimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)TransactionContext.getCurrentInstance();	

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

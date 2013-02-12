/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.elink.customizer;

import java.sql.SQLException;

import org.coder.alpha.query.exception.DeadLockException;
import org.coder.alpha.query.exception.UniqueConstraintException;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;


/**
 * Handles the JPQL exception.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JPQLExceptionHandler implements ExceptionHandler{
	
	/**
	 * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
	 */
	@Override
	public Object handleException(RuntimeException exception) {
		if(exception instanceof DatabaseException){
			DatabaseException de = (DatabaseException)exception;
			Throwable t = de.getInternalException();
			
			//基本的にDatabaseExceptionをスローした場合、この後でJPAが適切なPersistenceExceptionに変換する
			//DeadLockとか一意制約とか自動で変換されないやつだけここで変換する。
			
			if(t instanceof SQLException){
				SQLException sqle = (SQLException)t;
				int code = sqle.getErrorCode();
				//一意制約エラー
				if(code == getUniqueErrorCode()){
					throw new UniqueConstraintException(sqle);				
				//デッドロック
				}else if(code == getDeadLockErrorCode()){
					throw new DeadLockException(sqle);
				}				
			}
			throw de;			
		}else{
			throw exception;
		}
	}
	
	/**
	 * @return the error code that represents dead lock
	 */
	protected int getDeadLockErrorCode(){
		return 60;
	}
	
	/**
	 * @return the error code that represents unique constraint
	 */
	protected int getUniqueErrorCode(){
		return 1;
	}

}
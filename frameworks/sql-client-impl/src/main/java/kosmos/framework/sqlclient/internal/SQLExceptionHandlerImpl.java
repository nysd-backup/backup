/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal;

import java.sql.SQLException;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;

import kosmos.framework.sqlclient.api.exception.UniqueConstraintException;
import kosmos.framework.sqlengine.exception.ExceptionHandler;

/**
 * Handles the SQLException.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLExceptionHandlerImpl implements ExceptionHandler{
	
	/** 一意制約エラー */
	private int uniqueErrorCode = 1;
	
	/** 悲観ロックエラー */
	private int pessimisticErrorCode = 54;
	
	/** JDBCタイムアウトエラー */
	private int timeoutErrorCode = 1013;

	/** 悲観ロックタイムアウトエラー */
	private int lockTimeoutErrorCode = 30006;
	
	/**
	 * @see kosmos.framework.sqlengine.exception.ExceptionHandler#rethrow(java.lang.Throwable)
	 */
	@Override
	public RuntimeException rethrow(SQLException sqle) {
		
		int code = sqle.getErrorCode();
		//一意制約エラー
		if(code == uniqueErrorCode){
			throw new UniqueConstraintException(sqle);
		//リソースビジー	
		}else if(code == pessimisticErrorCode){
			throw new PessimisticLockException(sqle);
		//タイムアウト	
		}else if(code == timeoutErrorCode){
			throw new QueryTimeoutException(sqle);
		//悲観ロックタイムアウト
		}else if(code == lockTimeoutErrorCode){
			throw new LockTimeoutException(sqle);
		}
		throw new PersistenceException(sqle);
	
	}

	/**
	 * @param uniqueErrorCode the uniqueErrorCode to set
	 */
	public void setUniqueErrorCode(int uniqueErrorCode) {
		this.uniqueErrorCode = uniqueErrorCode;
	}

	/**
	 * @param pessimisticErrorCode the pessimisticErrorCode to set
	 */
	public void setPessimisticErrorCode(int pessimisticErrorCode) {
		this.pessimisticErrorCode = pessimisticErrorCode;
	}

	/**
	 * @param timeoutErrorCode the timeoutErrorCode to set
	 */
	public void setTimeoutErrorCode(int timeoutErrorCode) {
		this.timeoutErrorCode = timeoutErrorCode;
	}

	/**
	 * @param lockTimeoutErrorCode the lockTimeoutErrorCode to set
	 */
	public void setLockTimeoutErrorCode(int lockTimeoutErrorCode) {
		this.lockTimeoutErrorCode = lockTimeoutErrorCode;
	}

}

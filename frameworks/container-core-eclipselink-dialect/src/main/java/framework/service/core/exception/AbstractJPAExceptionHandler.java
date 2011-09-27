/**
 * Copyright 2011 the original author
 */
package framework.service.core.exception;

import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;
import org.eclipse.persistence.exceptions.OptimisticLockException;

/**
 * JPAで例外が発生した時のハンドリングを行う.
 * 
 * <pre>
 * ここで例外をにぎり潰した場合JPAのSessionのrollbackOnlyはtrueにならなくなる。
 * 握りつぶさない場合、flush実行時だけでなくトランザクション終了時にも呼び出されるため複数回実行されることを想定した処理とすること。
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractJPAExceptionHandler implements ExceptionHandler{

	/**
	 * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
	 */
	@Override
	public Object handleException(RuntimeException exception) {
		
		//楽観ロックエラー
		if(exception instanceof OptimisticLockException){
			return handleOptimisticLockException(OptimisticLockException.class.cast(exception));			
		//悲観ロックエラー
		}else if ( exception instanceof PessimisticLockException ){
			return handlePessimisticLockException(PessimisticLockException.class.cast(exception));		
		//SQLException（一意制約違反など）	
		}else if( exception instanceof DatabaseException){
			return handleDatabaseException(DatabaseException.class.cast(exception));
		}
		
		return handleOthers(exception);
		
	}
	
	/**
	 * @param e 楽観ロック例外
	 * @return オブジェクト
	 */
	protected Object handleOptimisticLockException(OptimisticLockException e){
		throw e;
	}
	
	/**
	 * @param e 悲観ロック例外
	 * @return オブジェクト
	 */
	protected Object handlePessimisticLockException(PessimisticLockException e){
		throw e;
	}
	
	/**
	 * @param e DB例外(SQLExceptionが発生している）
	 * @return オブジェクト
	 */
	protected Object handleDatabaseException(DatabaseException e){
		throw e;
	}
	
	/**
	 * @param e その他例外
	 * @return オブジェクト
	 */
	protected Object handleOthers(RuntimeException e){
		throw e;
	}
	

}

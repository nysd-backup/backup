package org.coder.alpha.query.exception;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;

/**
 * EclipseLinkの例外Handller . 
 * 
 * @author yoshida-n
 * @version 1.0
 */
public class DefaultExceptionHandler implements ExceptionHandler {

    /**
     *  一意制約エラー .
     */
    private static final int UNIQUE_ERR_CD = 1;

    /**
     *  悲観ロックエラー .
     */
    private static final int PESSIMISTIC_ERR_CD = 54;

    /**
     *  デッドロックエラー .
     */
    private static final int DEAD_LOCK_ERR_CD = 60;

    /**
     * JDBCタイムアウトエラー  .
     */
    private static final int TIMEOUT_ERR_CD = 1013;

    /**
     * 悲観ロックタイムアウトエラー .
     */
    private static final int LOCK_TIMEOUT_ERR_CD = 30006;

    /**
     * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
     */

    /**
     * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
     */
    @Override
    public Object handleException(RuntimeException exception) {
        if (exception instanceof DatabaseException) {
            int code = ((DatabaseException) exception).getDatabaseErrorCode();
            // 一意制約エラー
            if (code == UNIQUE_ERR_CD) {
                throw new UniqueConstraintException(exception);
                // リソースビジー
            } else if (code == PESSIMISTIC_ERR_CD) {
                throw new PessimisticLockException(exception);
                // タイムアウト
            } else if (code == TIMEOUT_ERR_CD) {
                throw new QueryTimeoutException(exception);
                // 悲観ロックタイムアウト
            } else if (code == LOCK_TIMEOUT_ERR_CD) {
                throw new LockTimeoutException(exception);
                // デッドロック
            } else if (code == DEAD_LOCK_ERR_CD) {
                throw new DeadLockException(exception);
            }
            throw new PersistenceException(exception);
        } else {
            throw exception;
        }
    }

}

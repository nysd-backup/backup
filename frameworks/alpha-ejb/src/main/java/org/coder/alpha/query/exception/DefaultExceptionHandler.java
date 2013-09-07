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
 * @author Administrator
 * @version 2013/05/02 新規作成
 */
public class DefaultExceptionHandler implements ExceptionHandler {

    /**
     * <pre>
     *   ■説明
     *      一意制約エラー .
     * </pre>
     */
    private static final int UNIQUE_ERR_CD = 1;

    /**
     * <pre>
     *   ■説明
     *      悲観ロックエラー .
     * </pre>
     */
    private static final int PESSIMISTIC_ERR_CD = 54;

    /**
     * <pre>
     *   ■説明
     *      デッドロック .
     * </pre>
     */
    private static final int DEAD_LOCK_ERR_CD = 60;

    /**
     * <pre>
     *   ■説明
     *      JDBCタイムアウトエラー .
     * </pre>
     */
    private static final int TIMEOUT_ERR_CD = 1013;

    /**
     * <pre>
     *   ■説明
     *      悲観ロックタイムアウトエラー .
     * </pre>
     */
    private static final int LOCK_TIMEOUT_ERR_CD = 30006;

    /**
     * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
     */

    /**
     * <pre>
     *    例外処理 .
     * </pre>
     * 
     * @param exception
     *            例外
     * @return Object
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

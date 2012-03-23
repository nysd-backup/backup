/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message.impl;

import javax.persistence.LockTimeoutException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;

import kosmos.framework.core.exception.SystemException;
import kosmos.framework.core.message.ExceptionMessageFactory;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageId;
import kosmos.framework.sqlclient.api.exception.DeadLockException;
import kosmos.framework.sqlclient.api.exception.UniqueConstraintException;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultExceptionMessageFactoryImpl implements ExceptionMessageFactory{

	/**
	 * @see kosmos.framework.core.message.ExceptionMessageFactory#getBizMessageFrom(java.lang.Throwable)
	 */
	@Override
	public MessageBean getBizMessageFrom(Throwable arg) {
		if(arg instanceof OptimisticLockException){
			return new MessageBean(MessageId.OPTIMISTIC_LOCK_ERROR);
		}
		if(arg instanceof PessimisticLockException){
			return new MessageBean(MessageId.RESOURCE_BUSY_ERROR);
		}
		if(arg instanceof DeadLockException){
			return new MessageBean(MessageId.DEAD_LOCK_ERROR);
		}
		if(arg instanceof UniqueConstraintException){
			return new MessageBean(MessageId.UNIQUE_CONSTRAINT_ERROR);
		}
		if(arg instanceof LockTimeoutException){
			return new MessageBean(MessageId.LOCK_TIMEOUT_ERROR);
		}
		if(arg instanceof QueryTimeoutException){
			return new MessageBean(MessageId.JDBC_TIMEOUT_ERROR);
		}
		if(arg instanceof Error){
			throw (Error)arg;
		}
		if(arg instanceof RuntimeException){
			throw (RuntimeException)arg;
		}
		throw new RuntimeException(arg);
	}

	/**
	 * @see kosmos.framework.core.message.ExceptionMessageFactory#getBizMessageFrom(java.lang.Throwable)
	 */
	@Override
	public MessageBean getSysMessageFrom(Throwable arg) {
		if(arg instanceof SystemException){
			SystemException se = (SystemException)arg;
			return new MessageBean(se.getMessageId(),se.getArgs());
		}
		if(arg instanceof PersistenceException){
			return new MessageBean(MessageId.PERSISTENCE_ERROR);
		}
		return new MessageBean(MessageId.OTHER_ERROR);
	}

}

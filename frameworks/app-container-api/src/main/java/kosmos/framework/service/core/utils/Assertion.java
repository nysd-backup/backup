/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.utils;

import java.util.List;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.TransactionManagingContext;


/**
 * Assertion.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Assertion {
	
	/**
	 * Fails the service.
	 * 
	 * @param bean the MessageBean
	 * @throws BusinessException the exception
	 */
	public void fail(MessageBean bean) {
		addError(bean);
		fail();
	}
		
	/**
	 * Fails the service.
	 * 
	 * @throws BusinessException the exception
	 */
	public void fail() {
		throw ServiceLocator.createDefaultBusinessException();
	}
		
	/**
	 * Fails the service if transaction is marked as rolled back.
	 * 
	 * @throws BusinessException the exception
	 */
	public void assertSuccess() {	
		TransactionManagingContext context = (TransactionManagingContext)ServiceContext.getCurrentInstance();
		boolean isRollbackOnly = context.getCurrentUnitOfWork().isRollbackOnly();
		if(isRollbackOnly){
			fail();
		}
	}
	
	/**
	 * Fails the service if size is over maxSize.
	 * 
	 * @param maxSize the maxSize
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public void assertLessMax(MessageBean bean ,List<?> result , int maxSize){
		if(result == null || result.isEmpty()){
			return;
		}
		if( result.size() > maxSize ){
			fail(bean);
		}
	}
		
	/**
	 * Fails the service if the result is empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public void assertExists(MessageBean bean ,List<?> result){
		if( result == null || result.isEmpty() ){
			fail(bean);
		}
	}
	
	/**
	 * Fails the service if the result is not empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public void assertEmpty(MessageBean bean ,List<?> result){
		if( result != null && ! result.isEmpty() ){
			fail(bean);
		}
	}
	
	/**
	 * Fails the service if value is null.
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public void assertExists(MessageBean bean ,Object value){
		if( value == null){
			fail(bean);
		}
	}
	
	/**
	 * Fails the service if value is not null.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public void assertNull(MessageBean bean ,Object value){
		if( value != null){
			fail(bean);
		}
	}
	
	/**
	 * @param bean the MessageBean
	 */
	private void addError(MessageBean bean) {
		String message = ServiceLocator.createDefaultMessageBuilder().load(bean);
		ServiceContext.getCurrentInstance().addError((ErrorMessage)bean.getMessage(), message);
	}
}

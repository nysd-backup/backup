/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.logics;

import java.util.Collection;
import java.util.List;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.exception.UnexpectedDataFoundException;
import kosmos.framework.core.exception.UnexpectedMultiResultException;
import kosmos.framework.core.exception.UnexpectedNoDataFoundException;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
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
	 * Tests the data is single result.
	 * 
	 * @param collection the collection
	 */
	public Assertion assertSingleResult(Collection<?> collection){
		if(collection != null && !collection.isEmpty() && collection.size() > 1){
			throw new UnexpectedMultiResultException("result size is " + collection.size());
		}
		return this;
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 */
	public Assertion assertEmpty(Collection<?> collection){
		if(collection != null && ! collection.isEmpty()){
			throw new UnexpectedDataFoundException();
		}
		return this;
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 */
	public Assertion assertExists(Collection<?> collection){
		if(collection == null || collection.isEmpty()){
			throw new UnexpectedNoDataFoundException();
		}
		return this;
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 */
	public Assertion assertExists(Object value){
		if(value == null ){
			throw new UnexpectedNoDataFoundException();
		}
		return this;
	}
	
	/**
	 * Fails the service.
	 * 
	 * @param bean the MessageBean
	 * @throws BusinessException the exception
	 */
	public void bizError(MessageBean bean) {
		addError(bean);
		bizError();
	}
		
	/**
	 * Fails the service.
	 * 
	 * @throws BusinessException the exception
	 */
	public void bizError() {
		throw ServiceLocator.createDefaultBusinessException();
	}
		
	/**
	 * Fails the service if transaction is marked as rolled back.
	 * 
	 * @throws BusinessException the exception
	 */
	public Assertion probablyCommitable() {	
		TransactionManagingContext context = (TransactionManagingContext)ServiceContext.getCurrentInstance();
		boolean isRollbackOnly = context.getCurrentUnitOfWork().isRollbackOnly();
		if(isRollbackOnly){
			bizError();
		}
		return this;
	}
	
	/**
	 * Fails the service if size is over maxSize.
	 * 
	 * @param maxSize the maxSize
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public Assertion probablyLessThanLimit(MessageBean bean ,List<?> result , int limit){
		if(result == null || result.isEmpty()){
			return this;
		}
		if( result.size() > limit ){
			bizError(bean);
		}
		return this;
	}
		
	/**
	 * Fails the service if the result is empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public Assertion probablyExists(MessageBean bean ,Collection<?> result){
		if( result == null || result.isEmpty() ){
			bizError(bean);
		}
		return this;
	}
	
	/**
	 * Fails the service if the result is not empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public Assertion probablyEmpty(MessageBean bean ,Collection<?> result){
		if( result != null && ! result.isEmpty() ){
			bizError(bean);
		}
		return this;
	}
	
	/**
	 * Fails the service if value is null.
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public Assertion probablyExists(MessageBean bean ,Object value){
		if( value == null){
			bizError(bean);
		}
		return this;
	}
	
	/**
	 * Fails the service if value is not null.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	public Assertion probablyEmpty(MessageBean bean ,Object value){
		if( value != null){
			bizError(bean);
		}
		return this;
	}
	
	/**
	 * @param bean the MessageBean
	 */
	private void addError(MessageBean bean) {
		MessageResult result = ServiceLocator.createDefaultMessageBuilder().load(bean);
		ServiceContext.getCurrentInstance().addMessage(result);
	}
}

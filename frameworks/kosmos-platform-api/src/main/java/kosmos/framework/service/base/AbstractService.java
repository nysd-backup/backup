/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import java.util.Collection;
import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.core.logics.Condition;
import kosmos.framework.core.logics.Converter;
import kosmos.framework.core.logics.log.LogWriter;
import kosmos.framework.core.logics.log.LogWriterFactory;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageLevel;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.TransactionManagingContext;
import kosmos.framework.sqlclient.api.exception.UnexpectedDataFoundException;
import kosmos.framework.sqlclient.api.exception.UnexpectedNoDataFoundException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 基底サービス.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractService {
	
	/** ログ */
	private final LogWriter logger = LogWriterFactory.getLog(this.getClass());
	
	/** 判断ユーティリティ */
	protected final Condition cond = new Condition();
	
	/** 変換ユーティリティ */
	protected final Converter conv = new Converter();
	
	/**
	 * @param message デバッグメッセージ
	 */
	protected final void debug(String message){
		logger.debug(message);
	}
	
	/**
	 * Tests the data is single result.
	 * 
	 * @param collection the collection
	 * @throws NonUniqueResultException
	 */
	protected final void assertSingleResult(Collection<?> collection){
		if(collection != null && !collection.isEmpty() && collection.size() > 1){
			throw new NonUniqueResultException("result size is " + collection.size());
		}
		
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 * @throws UnexpectedDataFoundException
	 */
	protected final void assertEmpty(Collection<?> collection){
		if(collection != null && ! collection.isEmpty()){
			throw new UnexpectedDataFoundException();
		}
		
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 * @throws UnexpectedNoDataFoundException
	 */
	protected final void assertExists(Collection<?> collection){
		if(collection == null || collection.isEmpty()){
			throw new UnexpectedNoDataFoundException();
		}
		
	}
	
	/**
	 * Tests the data exists.
	 * 
	 * @param collection the collection
	 * @throws UnexpectedNoDataFoundException
	 */
	protected final void assertExists(Object value){
		if(value == null ){
			throw new UnexpectedNoDataFoundException();
		}
		
	}
	
	/**
	 * Fails the service.
	 * 
	 * @param bean the MessageBean
	 * @throws BusinessException the exception
	 */
	public void throwBizError(MessageBean bean) {
		addError(bean);
		throwBizError();
	}
		
	/**
	 * Fails the service.
	 * 
	 * @throws BusinessException the exception
	 */
	public void throwBizError() {
		throw ServiceLocator.createDefaultBusinessException();
	}
		
	/**
	 * Fails the service if transaction is marked as rolled back.
	 * 
	 * @throws BusinessException the exception
	 */
	protected final void probablyCommitable() {	
		if(isRollbackOnly()){
			throwBizError();
		}		
	}
	
	/**
	 * Fails the service if size is over maxSize.
	 * 
	 * @param maxSize the maxSize
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	protected final void probablyLessThanLimit(MessageBean bean ,List<?> result , int limit){
		if(result == null || result.isEmpty()){
			return;
		}
		if( result.size() > limit ){
			throwBizError(bean);
		}		
	}
	
	
	/**
	 * Fails the service if the version no is unmatch.
	 * 
	 * @param holdingVersionNo 
	 * @param currentVersionNo
	 * @throws OptimisticLockException 
	 * @return
	 */
	protected final <T> void probablySameVersion(T holdingVersionNo , T currentVersionNo ){
		if(!holdingVersionNo.equals(currentVersionNo)){
			throw new OptimisticLockException("current version = " + currentVersionNo + " holdingVersion = " + holdingVersionNo);
		}		
	}
	
		
	/**
	 * Fails the service if the result is empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	protected final void probablyExists(MessageBean bean ,Collection<?> result){
		if( result == null || result.isEmpty() ){
			throwBizError(bean);
		}
		
	}
	
	/**
	 * Fails the service if the result is not empty.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	protected final void probablyEmpty(MessageBean bean ,Collection<?> result){
		if( result != null && ! result.isEmpty() ){
			throwBizError(bean);
		}
		
	}
	
	/**
	 * Fails the service if value is null.
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	protected final void probablyExists(MessageBean bean ,Object value){
		if( value == null){
			throwBizError(bean);
		}
		
	}
	
	/**
	 * Fails the service if value is not null.
	 * 
	 * @param bean the MessageBean
	 * @param result the result
	 * @throws BusinessException the exception
	 */
	protected final void probablyEmpty(MessageBean bean ,Object value){
		if( value != null){
			throwBizError(bean);
		}
		
	}
	
	/**
	 * @param bean the MessageBean
	 */
	private void addError(MessageBean bean) {
		MessageResult result = ServiceLocator.createDefaultMessageBuilder().load(bean);
		if(result.getLevel() < MessageLevel.E.ordinal()){
			bug();
		}
		ServiceContext.getCurrentInstance().addMessage(result);
	}
	
	/**
	 * @param bean
	 */
	protected final void addMessage(MessageBean bean){
		MessageResult result = ServiceLocator.createDefaultMessageBuilder().load(bean);
		ServiceContext.getCurrentInstance().addMessage(result);
	}
	
	/**
	 * @param messageCode
	 * @param args
	 * @return
	 */
	protected final MessageBean createMessage(int messageCode , Object... args){
		return new MessageBean(messageCode, args);
	}
	
	/**
	 * 
	 */
	protected final void bug(){
		throw new PoorImplementationException();
	}
	
	/**
	 * @return
	 */
	protected final boolean isRollbackOnly(){
		TransactionManagingContext context = (TransactionManagingContext)ServiceContext.getCurrentInstance();
		return context.getCurrentUnitOfWork().isRollbackOnly();
	}
	
	/**
	 * @param dest
	 * @param orig
	 */
	protected final void copyObject(Object dest , Object orig){
		if(orig instanceof AbstractEntity && dest instanceof AbstractEntity && dest.getClass().equals(orig.getClass())){
			throw new UnsupportedOperationException("Use the AbstractEntity#clone");
		}
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e){
			throw new PoorImplementationException("invalid bean", e);
		}
	}
	
	
}

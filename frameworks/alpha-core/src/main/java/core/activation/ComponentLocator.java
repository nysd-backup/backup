/**
 * Copyright 2011 the original author
 */
package core.activation;

import core.exception.BusinessException;
import core.logics.log.FaultNotifier;
import core.message.ExceptionMessageFactory;
import core.message.MessageBuilder;



/**
 * Creates the components.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ComponentLocator {
	
	/** the ComponentLocator */
	protected static ComponentLocator  delegate;
	
	/**
	 * @return the message builder
	 */
	public abstract MessageBuilder createMessageBuilder();	
	
	/**
	 * @return the fault notifier
	 */
	public abstract FaultNotifier createFaultNotifier();	
	
	/**
	 * @return the ExceptionMessageFactory
	 */
	public abstract ExceptionMessageFactory createExceptionMessageFactory();	
	
	/**
	 * @return the BusinessException
	 */
	public abstract BusinessException createBusinessException();
	
	/**
	 * @return the MessageBuilder
	 */
	public static MessageBuilder createDefaultMessageBuilder(){
		return delegate.createMessageBuilder();
	}
	
	/**
	 * @return the BusinessException
	 */
	public static BusinessException createDefaultBusinessException(){
		return delegate.createBusinessException();
	}
	
	/**
	 * @return the fault error
	 */
	public static FaultNotifier createDefaultFaultNotifier(){
		return delegate.createFaultNotifier();
	}
	
	/**
	 * @return the ExceptionMessageFactry
	 */
	public static ExceptionMessageFactory createDefaultExceptionMessageFactory(){
		return delegate.createExceptionMessageFactory();
	}
	
}

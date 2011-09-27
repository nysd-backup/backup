/**
 * Copyright 2011 the original author
 */
package framework.service.ext.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

import framework.core.exception.application.BusinessException;

/**
 * EJB用業務例外.
 * 
 * <pre>
 * BusinessExceptionのままだとシステムエ例外として扱われてしまうため、
 * ApplicationException(rollback=true)を設定した例外を用意する。
 *</pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ApplicationException(rollback=true)
public class EJBBusinessException extends BusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public EJBBusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message メッセージ
	 */
	public EJBBusinessException(String message){
		super(message);
	}
	
	/**
	 * @param message メッセージ
	 * @param reply リプライ
	 */
	public EJBBusinessException(String message,Serializable reply){
		super(message,reply);
	}
	
	/**
	 * @param reply リプライ
	 */
	public EJBBusinessException(Serializable reply){
		super(reply);
	}

}

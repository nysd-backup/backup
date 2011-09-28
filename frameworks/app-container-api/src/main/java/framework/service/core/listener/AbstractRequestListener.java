/**
 * Copyright 2011 the original author
 */
package framework.service.core.listener;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;
import framework.api.service.RequestListener;
import framework.core.exception.application.BusinessException;
import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.transaction.ServiceContext;

/**
 * リクエストリスナー.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractRequestListener implements RequestListener{

	/** ログ */
	private final LogWriter LOG = LogWriterFactory.getLog(getClass());
	
	/**
	 * @see framework.api.service.RequestListener#processService(framework.api.dto.RequestDto)
	 */
	@Override
	public ReplyDto processService(RequestDto dto) {
		
		ReplyDto reply = new ReplyDto();
		
		long startTime = System.currentTimeMillis();
		Throwable cause = null;
		
		ServiceContext context = createContext();
		context.initialize();
		context.setClientRequestBean(dto.getClientRequestBean());
		context.setClientSessionBean(dto.getClientSessionBean());
		try{
			
			Object service = getService(dto);
			Method m = dto.getTargetClass().getMethod(dto.getMethodName(), dto.getParameterTypes());
			Object replyData = m.invoke(service, (Object[])dto.getParameter());			
			reply.setMessageList(context.getMessageList());
			reply.setReply(Serializable.class.cast(replyData));
			
		}catch(Throwable e){
			cause = e;
			handle(e, reply,context);	
		}finally{
			terminate(startTime,cause);
			context.release();
		}
		
		return reply;
	}
	
	/**
	 * @param dto DTO
	 * @return サービス
	 */
	protected Object getService(RequestDto dto){
		if(dto.getAlias() != null){
			return ServiceLocator.lookup(dto.getAlias());
		}else{
			return ServiceLocator.lookupByInterface(dto.getTargetClass());
		}
	}
	
	/**
	 * コンテキスト取得
	 */
	protected abstract ServiceContext createContext();
	
	/**
	 * 終了処理
	 * @param startTime 開始時間
	 * @param cause 例外、正常時はnull
	 */
	protected void terminate(long startTime,Throwable cause){
		
	}
	
	/**
	 * 例外処理
	 * @param t　例外
	 * @param reply リプライ
	 * @param context コンテキスト
	 */
	protected void handle(Throwable t, ReplyDto reply ,ServiceContext context) {

		LOG.error(t);
		
		Throwable target = t;
		
		//業務例外はメッセージを詰めて返却
		if( t instanceof BusinessException){
			
			BusinessException se = (BusinessException)t;
			se.getReply().setMessageList(context.getMessageList());
			throw se;
			
		}else if (t instanceof InvocationTargetException){
			target = ((InvocationTargetException)t).getTargetException();			
			if( target instanceof BusinessException){			
				BusinessException se = (BusinessException)target;
				se.getReply().setMessageList(context.getMessageList());
				throw se;
			}
		}
	
		if(target instanceof Error){
			throw (Error)target;
		}else if(target instanceof RuntimeException ){
			throw (RuntimeException)target;
		}else {
			throw new IllegalStateException("unexpected exception",target);
		}
		
	}

}

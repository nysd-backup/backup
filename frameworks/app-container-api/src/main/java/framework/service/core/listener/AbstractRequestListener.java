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
 * 繝ｪ繧ｯ繧ｨ繧ｹ繝医Μ繧ｹ繝翫・.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractRequestListener implements RequestListener{

	/** 繝ｭ繧ｰ */
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
	 * @return 繧ｵ繝ｼ繝薙せ蜷・
	 */
	protected Object getService(RequestDto dto){
		if(dto.getAlias() != null){
			return ServiceLocator.lookup(dto.getAlias());
		}else{
			return ServiceLocator.lookupByInterface(dto.getTargetClass());
		}
	}
	
	/**
	 * 繧ｳ繝ｳ繝・く繧ｹ繝亥叙蠕・
	 */
	protected abstract ServiceContext createContext();
	
	/**
	 * 邨ゆｺ・・逅・
	 * @param startTime 髢句ｧ区凾髢・
	 * @param cause 萓句､悶∵ｭ｣蟶ｸ譎ゅ・null
	 */
	protected void terminate(long startTime,Throwable cause){
		
	}
	
	/**
	 * 萓句､門・逅・
	 * @param t縲萓句､・
	 * @param reply 繝ｪ繝励Λ繧､
	 * @param context 繧ｳ繝ｳ繝・く繧ｹ繝・
	 */
	protected void handle(Throwable t, ReplyDto reply ,ServiceContext context) {

		LOG.error(t);
		
		Throwable target = t;
		
		//讌ｭ蜍吩ｾ句､悶・繝｡繝・そ繝ｼ繧ｸ繧定ｩｰ繧√※霑泌唆
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

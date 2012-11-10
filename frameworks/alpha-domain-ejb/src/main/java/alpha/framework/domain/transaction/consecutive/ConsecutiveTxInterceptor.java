/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.consecutive;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.InvocationContext;

import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.advice.InvocationAdapterImpl;
import alpha.framework.domain.transaction.AbstractTxInterceptor;
import alpha.framework.domain.transaction.DomainContext;


/**
 * ConsecutiveTxInterceptor.
 * <pre>
 * Don't add the message in autonomous transaction.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveTxInterceptor extends AbstractTxInterceptor{

	@Resource
	private EJBContext sessionContext;

	/**
	 * @see alpha.framework.domain.transaction.AbstractTxInterceptor#invokeRoot(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invokeRoot(InvocationContext ic) throws Throwable {
		DomainContext context = createServiceContext();
		try{		
			context.initialize();		
			initialize(ic,context);
							
			Object returnValue = proceed(ic);
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return editResponse( returnValue, context);
		}catch(Throwable e){
			sessionContext.setRollbackOnly();
			return handleException(ic.getMethod().getReturnType(),e,context);			
		}finally{				
			context.release();		
			terminate();
		}
	}
	
	/**
	 * Initialize the transaction.
	 * @param ic
	 */
	protected void initialize(InvocationContext ic,DomainContext sc){		
	}
	
	/**
	 * Terminate the Transaction
	 */
	public void terminate() {		
	}
	
	/**
	 * Handles the exception
	 * @param e
	 * @param context
	 * @return
	 */
	protected Object handleException(Class<?> returnType ,Throwable e,DomainContext context)throws Throwable{
		throw e;
	}
	
	/**
	 * Edits the response.
	 * @param invocationSource the source
	 * @param returnValue the returnValue
	 * @param context the context
	 * @return the edited returnValue
	 */
	protected Object editResponse(Object returnValue, DomainContext context){
		return returnValue;
	}
	
	/**
	 * @return the alpha.domain context
	 */
	protected DomainContext createServiceContext(){
		return new ConsecutiveTxContext();
	}

	/**
	 * @see alpha.framework.domain.transaction.AbstractTxInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	protected Object proceed(InvocationContext ic) throws Throwable{
		try{
			InternalPerfInterceptor interceptor = new InternalPerfInterceptor();
			if(interceptor.isEnabled()){
				return interceptor.around(new InvocationAdapterImpl(ic));
			}else {
				return ic.proceed();
			}
		}catch(Exception e){
			wrapByAppException(e);
			return null;
		}
	}

	/**
	 * wrap by application exception if needed.
	 * 
	 * @param e 
	 * @throws Exception
	 */
	protected void wrapByAppException(Exception e)throws Exception{
		throw e;
	}

}

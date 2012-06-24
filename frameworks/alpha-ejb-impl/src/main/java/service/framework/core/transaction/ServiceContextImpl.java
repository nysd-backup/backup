/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import java.util.Stack;



/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContextImpl extends ServiceContext{

	private Stack<InternalUnitOfWork> unitOfWorkStack = new Stack<InternalUnitOfWork>();;
	
	/**
	 * @see service.framework.core.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		getCurrentUnitOfWork().setRollbackOnly();
	}
	
	/**
	 * @see service.framework.core.transaction.ServiceContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly(){
		return getCurrentUnitOfWork().isRollbackOnly();
	}

	/**
	 * @return current unitOfWorkStack
	 */
	public InternalUnitOfWork getCurrentUnitOfWork(){
		return unitOfWorkStack.peek();
	}
	
	/**
	 * Start unit of work.
	 */
	public void startUnitOfWork(){
		unitOfWorkStack.push(new InternalUnitOfWork());
	}
	
	/**
	 * Finish unit of work.
	 */
	public void endUnitOfWork(){
		unitOfWorkStack.pop();
	}

	/**
	 * @see service.framework.core.transaction.ServiceContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		unitOfWorkStack = new Stack<InternalUnitOfWork>();		
	}
	
	/**
	 * @see service.framework.core.transaction.ServiceContext#release()
	 */
	@Override
	public void release(){	
		unitOfWorkStack.clear();
		unitOfWorkStack = null;
		super.release();				
	}
}

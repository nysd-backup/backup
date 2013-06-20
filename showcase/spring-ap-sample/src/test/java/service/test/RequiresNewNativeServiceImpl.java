/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import org.coder.alpha.message.context.MessageContext;
import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import service.core.BusinessException;
import service.test.entity.TestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RequiresNewNativeServiceImpl implements RequiresNewNativeService{

	@Autowired
	private EntityManager per;
	
	@PostConstruct
	protected void postConstruct(){
		
	}
	
	public String test() {
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		per.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		rollbackOnly =  TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {
		;
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			Map<String,Object> hints = new HashMap<String,Object>();
			hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
			per.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

	/**
	 * @see alpha.domain.framework.test.RequiresNewService#addMessage()
	 */
	@Override
	public void addMessage() {
		MessageContext.getCurrentInstance().acceptRollbackTrigger( new RollbackableImpl("100"));
		rollbackOnly =  TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

	@Autowired(required=false)
	private RequireService service;
	
	@Autowired(required=false)
	private RequireService service2;
	
	@Autowired(required=false)
	private RequiresNewService newService;
	
	/**
	 * @see alpha.domain.framework.test.RequiresNewService#callTwoServices()
	 */
	@Override
	public void callTwoServices() {
		
		//業務例外化
		//RequireService service = getService(RequireService.class);
		service.addMessage();
		
		//永続化
		//RequireService service2 = getService(RequireService.class);
		state= service2.persist();		
		
		rollbackOnly = TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		
	}
	
	private boolean rollbackOnly = false;

	private int state = 0;

	public int getState(){
		return state;
	}

	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

	/**
	 * @see alpha.domain.framework.test.RequiresNewService#throwError()
	 */
	@Override
	public void throwError() {
		throw new BusinessException("error");
	}
	

	@Override
	public String another() {
		//RequiresNewService service = ServiceLocator.getService(RequiresNewService.class);;
		newService.addMessage();
		if(!newService.isRollbackOnly()){
			throw new IllegalStateException();
		}
		rollbackOnly = TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		return "OK";
	}

}

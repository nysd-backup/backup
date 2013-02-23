/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PessimisticLockException;

import org.coder.alpha.framework.transaction.TransactionContext;
import org.coder.alpha.query.criteria.EntityManagerImpl;
import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class RequiresNewServiceImpl implements RequiresNewService, ApplicationContextAware{

	@PersistenceContext(unitName="oracle")
	private EntityManager per;
	
	@PostConstruct
	protected void postConstruct(){
		if( per == null){
			per = new EntityManagerImpl();
		}
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
		TransactionContext.getCurrentInstance().addMessage(new RollbackableImpl("100"));
		rollbackOnly =  TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}
	
	@Autowired(required=false)
	private RequireService service;
	
	@Autowired(required=false)
	private RequireService service2;
	

	/**
	 * @see alpha.domain.framework.test.RequiresNewService#callTwoServices()
	 */
	@Override
	public void callTwoServices() {
		
		//業務例外化
		//RequireService service = ServiceLocator.getService(RequireService.class);
		service.addMessage();
		
		//永続化
		//RequireService service2 = ServiceLocator.getService(RequireService.class);
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
		RequiresNewService newService = context.getBean(RequiresNewService.class);;
		newService.addMessage();
		if(!newService.isRollbackOnly()){
			throw new IllegalStateException();
		}
		rollbackOnly = TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		return "OK";
	}

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
		
	}

}

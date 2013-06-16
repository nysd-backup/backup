/**
 * Copyright 2011 the original author
 */
package service.services;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import org.coder.alpha.framework.transaction.NestedTransactionContext;
import org.coder.alpha.framework.transaction.TransactionContext;
import org.eclipse.persistence.config.QueryHints;

import service.Registry;
import service.entity.TargetEntity;
import service.testcase.BaseCase;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequiresNewServiceImpl extends BaseCase implements RequiresNewService{

	public String test() {		
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		em.find(TargetEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		rollbackOnly =  ((NestedTransactionContext)TransactionContext.getCurrentInstance()).isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {		
	
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			Map<String,Object> hints = new HashMap<String,Object>();
			hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
			em.find(TargetEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
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
		TransactionContext.getCurrentInstance().acceptRollbackTrigger(new RollbackableImpl("100"));
		rollbackOnly =  ((NestedTransactionContext)TransactionContext.getCurrentInstance()).isRollbackOnly();
	}

	/**
	 * @see alpha.domain.framework.test.RequiresNewService#callTwoServices()
	 */
	@Override
	public void callTwoServices() {
		
		//業務例外化
		RequireService service = Registry.getComponentFinder().getBean(RequireService.class);
		service.addMessage();
		
		//永続化
		RequireService service2 = Registry.getComponentFinder().getBean(RequireService.class);
		state= service2.persist();		
		
		rollbackOnly = ((NestedTransactionContext)TransactionContext.getCurrentInstance()).isRollbackOnly();
		
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
	public void persist() {		
		TargetEntity result = em.find(TargetEntity.class,"1");
		if(result == null){
			TargetEntity e = new TargetEntity();
			e.setTest("1");
			e.setAttr("aa");
			e.setAttr2(22);
			persist(e);
		}
	}

}

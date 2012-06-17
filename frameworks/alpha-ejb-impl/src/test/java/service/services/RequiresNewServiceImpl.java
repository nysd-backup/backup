/**
 * Copyright 2011 the original author
 */
package service.services;

import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;



import org.eclipse.persistence.config.QueryHints;

import client.sql.elink.EntityManagerProvider;
import client.sql.orm.OrmQueryFactory;
import client.sql.orm.OrmSelect;

import core.exception.BusinessException;
import core.message.MessageBean;
import core.message.MessageResult;

import service.entity.TestEntity;
import service.framework.core.activation.ServiceLocator;
import service.framework.core.activation.ServiceLocatorImpl;
import service.framework.core.transaction.ServiceContext;
import service.framework.core.transaction.ServiceContextImpl;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequiresNewServiceImpl implements RequiresNewService{

	public String test() {
		OrmQueryFactory ormQueryFactory = ServiceLocatorImpl.createDefaultOrmQueryFactory();
		OrmSelect<TestEntity> query = ormQueryFactory.createSelect(TestEntity.class);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0).find("1");
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {
		OrmQueryFactory ormQueryFactory = ServiceLocatorImpl.createDefaultOrmQueryFactory();	
		OrmSelect<TestEntity> query = ormQueryFactory.createSelect(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0).find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

	/**
	 * @see service.framework.test.RequiresNewService#addMessage()
	 */
	@Override
	public void addMessage() {
		MessageBean bean = new MessageBean("100");
		MessageResult message = ServiceLocator.createDefaultMessageBuilder().load(bean,Locale.getDefault());
		ServiceContext.getCurrentInstance().addMessage(message);	
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
	}

	/**
	 * @see service.framework.test.RequiresNewService#callTwoServices()
	 */
	@Override
	public void callTwoServices() {
		
		//業務例外化
		RequireService service = ServiceLocator.lookupByInterface(RequireService.class);
		service.addMessage();
		
		//永続化
		RequireService service2 = ServiceLocator.lookupByInterface(RequireService.class);
		state= service2.persist();		
		
		rollbackOnly = ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
		
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
	 * @see service.framework.test.RequiresNewService#throwError()
	 */
	@Override
	public void throwError() {
		throw new BusinessException("error");
	}
	
	@EJB
	private EntityManagerProvider per;

	@Override
	public void persist() {
		OrmQueryFactory ormQueryFactory = ServiceLocatorImpl.createDefaultOrmQueryFactory();	
		TestEntity result = ormQueryFactory.createSelect(TestEntity.class).find("1");
		if(result == null){
			TestEntity e = new TestEntity();
			e.setTest("1");
			e.setAttr("aa");
			e.setAttr2(22);
			per.getEntityManager().persist(e);
		}
	}

}

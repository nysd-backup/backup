/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.query.orm.StrictQuery;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.locator.AbstractServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;

import org.eclipse.persistence.config.QueryHints;


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
		AdvancedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0).find("1");
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {
		AdvancedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();	
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0).find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

	/**
	 * @see kosmos.framework.service.test.RequiresNewService#addMessage()
	 */
	@Override
	public void addMessage() {
		String message = ServiceLocator.createDefaultMessageBuilder().load(new ErrorMessage(100), Locale.getDefault());
		ServiceContext.getCurrentInstance().addError(new ErrorMessage(1),message);	
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
	}

	/**
	 * @see kosmos.framework.service.test.RequiresNewService#callTwoServices()
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
	 * @see kosmos.framework.service.test.RequiresNewService#throwError()
	 */
	@Override
	public void throwError() {
		throw new BusinessException("error");
	}
	
	@EJB
	private EntityManagerProvider per;

	@Override
	public void persist() {
		AdvancedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();	
		TestEntity result = ormQueryFactory.createStrictQuery(TestEntity.class).find("1");
		if(result == null){
			TestEntity e = new TestEntity();
			e.setTest("1");
			e.setAttr("aa");
			e.setAttr2(22);
			per.getEntityManager().persist(e);
		}
	}

}

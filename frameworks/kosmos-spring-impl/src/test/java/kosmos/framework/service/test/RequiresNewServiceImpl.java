/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.core.message.Messages;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;
import kosmos.framework.service.test.entity.TestEntity;

import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RequiresNewServiceImpl implements RequiresNewService{

	@Resource
	private LimitedOrmQueryFactory ormQueryFactory;
	
	@Autowired
	private MessageBuilder builder;
	
	public String test() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0).find("1");
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {
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
		MessageBean bean = new MessageBean(Messages.MSG_SYS_UNEXPECTED_DATA_FOUND);
		MessageResult message = builder.load(bean);
		ServiceContext.getCurrentInstance().addError(message);
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

}

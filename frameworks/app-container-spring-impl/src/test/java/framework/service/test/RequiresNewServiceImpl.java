/**
 * Use is subject to license terms.
 */
package framework.service.test;

import javax.annotation.Resource;
import javax.persistence.PessimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.core.exception.application.BusinessException;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.ServiceContextImpl;
import framework.service.test.entity.TestEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RequiresNewServiceImpl implements RequiresNewService{

	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;
	
	@Autowired
	private MessageAccessor<MessageBean> accessor;
	
	public String test() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.findWithLockNoWait("1");
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
		return "OK";
	}

	@Override
	public String crushException() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰していなければJPASessionのロールバックフラグはtrueになる
			query.findWithLockNoWait("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

	/**
	 * @see framework.service.test.RequiresNewService#addMessage()
	 */
	@Override
	public void addMessage() {
		accessor.addMessage(accessor.createMessage(1));	
		rollbackOnly =  ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly();
	}

	/**
	 * @see framework.service.test.RequiresNewService#callTwoServices()
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
	 * @see framework.service.test.RequiresNewService#throwError()
	 */
	@Override
	public void throwError() {
		throw new BusinessException("error");
	}

}

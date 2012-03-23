/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.InternalUnitOfWork;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.test.RequiresNewService;
import kosmos.framework.service.test.ServiceUnit;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalTransactionalTest extends ServiceUnit{
	
	/**
	 * 別トランザクションでエラーがあっても現在トランザクションには影響なぁE
	 */
	@Test
	@Rollback(false)
	public void addMessageInNewTransaction(){
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		service.addMessage();
		assertTrue(service.isRollbackOnly());
		assertFalse( ((ServiceContext)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());			
		
	}
	
	/**
	 * 別トランザクションでBusinessExceptionスローしてもキャチE��すれば現在トランザクションには影響なぁE
	 */
	@Test
	@Rollback(false)
	public void throwInNewTransaction(){
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			service.throwError();
			fail();
		}catch(BusinessException be){
			be.printStackTrace();
			assertEquals("error",be.getMessage());
		}
		assertFalse( ((ServiceContext)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());			
		
	}
	
	/**
	 *　呼び出し�Eの別トランザクション冁E��メチE��ージ追加�E�永続化してもこのトランザクションには影響なぁE��E
	 * また、呼び出し�EでSessionBeanのようにサービスがコールできなぁE��ぁE��こともなぁE��E
	 */
	@Test
	@Rollback(false)
	public void addMessageAndPersistInNewTransaction(){
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.callTwoServices();
		
		assertEquals(1,service.getState());
		assertTrue(service.isRollbackOnly());
		assertFalse( ((ServiceContext)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());
		
	}
	
	/**
	 *　現在のトランザクションで失敗して、次のトランザクションで成功しても現在は異常のまま
	 */
	@Test
	@Rollback(false)
	public void errorInCurrentAfterSuccessInNew(){
		
		ServiceContext context = getContext();
		InternalUnitOfWork internal = context.getCurrentUnitOfWork();
		context.setRollbackOnlyToCurrentTransaction();
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.test();
		
		assertFalse(service.isRollbackOnly());
		assertTrue(context.getCurrentUnitOfWork().isRollbackOnly());
		assertEquals(internal,getContext().getCurrentUnitOfWork());
	}
	
	
	private ServiceContext getContext(){
		return ServiceContext.getCurrentInstance();
	}
	
}

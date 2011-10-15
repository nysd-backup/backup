/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import framework.core.exception.BusinessException;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.transaction.InternalUnitOfWork;
import framework.service.core.transaction.ServiceContext;
import framework.service.core.transaction.TransactionManagingContext;
import framework.service.ext.transaction.ServiceContextImpl;
import framework.service.test.RequiresNewService;
import framework.service.test.ServiceUnit;

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
		assertFalse( ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());			
		
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
		assertFalse( ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());			
		
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
		assertFalse( ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());
		
	}
	
	/**
	 *　現在のトランザクションで失敗して、次のトランザクションで成功しても現在は異常のまま
	 */
	@Test
	@Rollback(false)
	public void errorInCurrentAfterSuccessInNew(){
		
		TransactionManagingContext context = getContext();
		InternalUnitOfWork internal = context.getCurrentUnitOfWork();
		context.setRollbackOnlyToCurrentTransaction();
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.test();
		
		assertFalse(service.isRollbackOnly());
		assertTrue(context.getCurrentUnitOfWork().isRollbackOnly());
		assertEquals(internal,getContext().getCurrentUnitOfWork());
	}
	
	
	private TransactionManagingContext getContext(){
		return ((TransactionManagingContext)ServiceContext.getCurrentInstance());
	}
	
}

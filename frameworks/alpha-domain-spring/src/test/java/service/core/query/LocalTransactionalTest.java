/**
 * Copyright 2011 the original author
 */
package service.core.query;


import javax.persistence.EntityManager;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import alpha.framework.core.exception.BusinessException;
import alpha.framework.domain.activation.ServiceLocator;

import service.test.RequiresNewService;
import service.test.ServiceUnit;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
@Transactional
public class LocalTransactionalTest extends ServiceUnit{
	
	/**
	 * 別トランザクションでエラーがあっても現在トランザクションには影響なぁE
	 */
	@Test
	@Rollback(false)
	public void addMessageInNewTransaction(){
		RequiresNewService service = ServiceLocator.getService(RequiresNewService.class);
		
		service.addMessage();
		assertTrue(service.isRollbackOnly());
			
	}
	
	/**
	 * 別トランザクションでBusinessExceptionスローしてもキャチE��すれば現在トランザクションには影響なぁE
	 */
	@Test
	@Rollback(false)
	public void throwInNewTransaction(){
		RequiresNewService service = ServiceLocator.getService(RequiresNewService.class);
		
		try{
			service.throwError();
			fail();
		}catch(BusinessException be){
			be.printStackTrace();
			assertEquals("error",be.getMessage());
		}
			
	}
	
	/**
	 *　呼び出し�Eの別トランザクション冁E��メチE��ージ追加�E�永続化してもこのトランザクションには影響なぁE��E
	 * また、呼び出し�EでSessionBeanのようにサービスがコールできなぁE��ぁE��こともなぁE��E
	 */
	@Test
	@Rollback(false)
	@Transactional
	public void addMessageAndPersistInNewTransaction(){
		
		RequiresNewService service = ServiceLocator.getService(RequiresNewService.class);
		service.callTwoServices();
		
		assertEquals(1,service.getState());
		assertTrue(service.isRollbackOnly());
			
	}
	
	/**
	 *　現在のトランザクションで失敗して、次のトランザクションで成功しても現在は異常のまま
	 */
	@Test
	@Rollback(false)
	public void errorInCurrentAfterSuccessInNew(){
	
		RequiresNewService service = ServiceLocator.getService(RequiresNewService.class);
		assertEquals("OK",service.another());
		assertFalse(service.isRollbackOnly());		

	}

	@Override
	protected EntityManager getEntityManager() {
		return null;
	}

	
}

/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import service.test.DupplicateService;
import service.test.ServiceUnit;
import service.test.entity.ITestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */

@ContextConfiguration(locations = 
{"/META-INF/context/oracleAgentApplicationContext.xml",
		"/META-INF/context/readonlyConnectionApplicationContext.xml"}		
)
public class DuplicateConnectionLocalEntityQueryTest extends ServiceUnit implements ITestEntity{
	
	@Autowired
	private DupplicateService service;
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Ignore
	@Test
	public void normal(){	
		
		setUpData("TEST.xls");
		int[] res = service.test();//ServiceLocator.getService(DupplicateService.class).test();		
		assertEquals(0,res[0]);
		assertEquals(3,res[1]);
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void failpersist(){	
		
		try{
			//ServiceLocator.getService(DupplicateService.class).fail();	
			service.fail();
			fail();
		}catch(TransactionRequiredException tre){
			tre.printStackTrace();
		}
	}

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

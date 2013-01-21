/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import alpha.framework.registry.ServiceLocator;

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
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Ignore
	@Test
	public void normal(){	
		
		setUpData("TEST.xls");
		int[] res = ServiceLocator.getService(DupplicateService.class).test();		
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
			ServiceLocator.getService(DupplicateService.class).fail();	
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

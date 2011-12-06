/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.SQLException;

import javax.persistence.TransactionRequiredException;

import kosmos.framework.service.test.DupplicateService;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.ITestEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


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
	@Test
	public void normal(){	
		
		setUpData("TEST.xls");
		int[] res = service.test();		
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
			service.fail();	
			fail();
		}catch(TransactionRequiredException tre){
			tre.printStackTrace();
		}
	}
	
	
}

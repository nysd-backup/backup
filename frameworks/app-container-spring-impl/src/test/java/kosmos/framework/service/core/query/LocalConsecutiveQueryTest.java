/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.util.List;

import kosmos.framework.core.services.ConsecutiveQueryService;
import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.service.test.SampleNativeQuery;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.TestEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalConsecutiveQueryTest extends ServiceUnit{
	
	@Autowired
	private ConsecutiveQueryService service;
	
	/**
	 * 
	 */
	@Test
	public void onlyNative(){
		
		setUpData("test.xls");
		
		QueryRequest r1 = new QueryRequest(SampleNativeQuery.class);
		r1.setParam("test",1);
		r1.setBranchParam("test",1);
		
		QueryRequest r2 = new QueryRequest(SampleNativeQuery.class);
		r2.setParam("test",45);
		r2.setBranchParam("test",45);		
		
		JPAOrmCondition<TestEntity> c = new JPAOrmCondition<TestEntity>(TestEntity.class);
		
		List<List<Object>> results = service.getResultLists(r1,r2,c);
		List<Object> e1 = results.get(0);
		List<Object> e2 = results.get(1);
		List<Object> e3 = results.get(2);
		assertFalse(e1.isEmpty());
		assertTrue(e2.isEmpty());
		assertEquals(2,e3.size());	
		
	}
	
	/**
	 * 
	 */
	@Test
	public void consecutive(){
		
		setUpData("test.xls");
		
		QueryRequest r1 = new QueryRequest(SampleNativeQuery.class);
		r1.setParam("test",1);
		r1.setBranchParam("test",1);
		
		QueryRequest r2 = new QueryRequest(SampleNativeQuery.class);

		List<List<Object>> results = service.getChainedResultLists(r1,r2);
		List<Object> e1 = results.get(0);
		List<Object> e2 = results.get(1);
		assertFalse(e1.isEmpty());
		assertEquals(1,e2.size());
		
	}
	
}

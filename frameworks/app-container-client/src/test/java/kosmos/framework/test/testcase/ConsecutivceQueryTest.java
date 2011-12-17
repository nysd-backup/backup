/**
 * Copyright 2011 the original author
 */
package kosmos.framework.test.testcase;

import java.util.List;

import javax.annotation.Resource;

import kosmos.framework.client.query.ConsecutiveQuery;
import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.services.ConsecutiveQueryService;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.test.client.SampleNativeQuery;
import kosmos.framework.test.entity.TestEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@ContextConfiguration(locations = "/META-INF/oracleAgentApplicationContext.xml")
public class ConsecutivceQueryTest extends ServiceUnit {
	
	@Autowired
	private ConsecutiveQueryService service;
	
	@Resource
	private QueryFactory remoteQueryFactory;
	
	@Resource
	private AdvancedOrmQueryFactory remoteOrmQueryFactory;
	
	/**
	 * Gets the result list.
	 */
	@Test
	public void getResultLists(){
		ConsecutiveQuery consecutiveQuery = new ConsecutiveQuery(service);
		setUpData("TEST.xls");
		StrictQuery<TestEntity> ormq = remoteOrmQueryFactory.createStrictQuery(TestEntity.class);
		StrictQuery<TestEntity> ormq2 = remoteOrmQueryFactory.createStrictQuery(TestEntity.class);		
		SampleNativeQuery q = remoteQueryFactory.createQuery(SampleNativeQuery.class);
		List<List<Object>> result = consecutiveQuery.getResultLists(ormq,q,ormq2);
		
		assertEquals(3,result.size());
	
	}

}

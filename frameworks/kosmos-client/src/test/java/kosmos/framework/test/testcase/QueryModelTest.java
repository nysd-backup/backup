/**
 * Copyright 2011 the original author
 */
package kosmos.framework.test.testcase;

import kosmos.framework.client.service.ServiceFacade;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.querymodel.QueryModel;
import kosmos.framework.querymodel.QueryProcessorFacade;
import kosmos.framework.querymodel.StrictQueryModel;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.test.entity.ITestEntity;
import kosmos.framework.test.entity.TestEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryModelTest extends ClientUnit{
	
	@SuppressWarnings("unused")
	@Autowired
	private QueryFactory clientQueryFactory; 
	
	@Autowired
	private LimitedOrmQueryFactory clientOrmQueryFactory; 
	
	@ServiceFacade
	private QueryProcessorFacade facade;
	
	@Test
	public void test(){
		
		//クエリ1
		StrictQuery<TestEntity> query = clientOrmQueryFactory.createStrictQuery(TestEntity.class);
		query.eq(ITestEntity.TEST, "1");	
		QueryModel model = new StrictQueryModel(query);
	
		//クエリ2
		StrictQuery<TestEntity> query2 = clientOrmQueryFactory.createStrictQuery(TestEntity.class);
		query.eq(ITestEntity.TEST, "2");	
		QueryModel model2 = new StrictQueryModel(query2);
		
		//関連つける
		model.setChild(model2);
		
		//実行
		facade.execute(model);
		
	}

}

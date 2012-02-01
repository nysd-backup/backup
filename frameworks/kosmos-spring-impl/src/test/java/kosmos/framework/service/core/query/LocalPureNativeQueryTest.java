/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;

import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.OrmQueryWrapperFactory;
import kosmos.framework.service.test.CachableConst;
import kosmos.framework.service.test.SampleNativeQuery;
import kosmos.framework.service.test.SampleNativeQueryConst;
import kosmos.framework.service.test.SampleNativeResult;
import kosmos.framework.service.test.SampleNativeUpdate;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.ITestEntity;
import kosmos.framework.service.test.entity.TestEntity;
import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.PersistenceManager;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


/**
 * SQLエンジンのチE��チE
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentNativeApplicationContext.xml")
public class LocalPureNativeQueryTest extends ServiceUnit implements ITestEntity{
	
	@Resource
	private QueryFactory queryFactory;
	
	@Resource
	private OrmQueryWrapperFactory ormQueryFactory;

	@Autowired
	private ConnectionProvider provider;
	
	@Autowired
	private PersistenceManager pm;
	
	/**
	 * @see kosmos.framework.service.test.ServiceUnit#setUpData(java.lang.String)
	 */
	@Override
	protected void setUpData(String dataPath){
		
		try{
			Connection con = provider.getConnection();
			String userName = con.getMetaData().getUserName();
			connection = new DatabaseConnection(con,userName);
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 通常検索
	 */
	@Test
	public void select(){
		
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setTest("1");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		
		result.get(0).setAttr("500");

		SampleNativeResult results = query.getSingleResult();
		assertEquals("3",results.getAttr());		
	}
	
	/**
	 * 通常検索if刁E
	 */
	@Test
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr("1000");
		query.setTest("1");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
	}
	

	/**
	 * if斁E��索
	 * 数値比輁E��not null、文字�E比輁E
	 */
	@Test
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
	}

	/**
	 * setFirstResult 
	 */
	@Test
	public void setFirstResultAndMaxResult(){
		setUpData("TEST.xls");
		
		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		pm.insert(f,new PersistenceHints());
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(1);
		pm.insert(s,new PersistenceHints());
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		pm.insert(t,new PersistenceHints());
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);		
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());
		assertEquals("900",result.get(1).getAttr());
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setTest("1");
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constAttr(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setTest("2");
		c.setAttr(CachableConst.TARGET_TEST_1_OK);
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constVersionNo(){
	
		setUpData("TEST.xls");
		EasyQuery<TestEntity> eq = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity entity = eq.eq(TEST, "1").getSingleResult();
		TestEntity updatable = entity.clone();
		updatable.setAttr2(CachableConst.TARGET_INT);
		pm.update(updatable,  new PersistenceHints().setFoundEntity(entity));
		
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setArc(CachableConst.TARGET_INT);		
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
	}
	
	/**
	 * ヒット件数等取征E
	 */
	@Test
	public void count(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		assertEquals(2,query.count());
	}
	
	/**
	 * test
	 */
	@Test 
	public void updateConstTest(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setTest("1");
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		EasyQuery<TestEntity> e = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity res = e.eq(TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * test
	 */
	@Test 
	public void batchUpdate(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		for(int i = 1  ; i <= 31; i++){
			update.setTest("1");	
			update.setAttr2set(900+i);			
			update.addBatch();
		}
		int[] res= update.batchUpdate();
		assertEquals(31,res.length);
		for(int r : res){
			assertEquals(-2,r);
		}
		
		EasyQuery<TestEntity> query = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity findedEntity = query.eq(TEST, "1").getSingleResult();
		assertEquals(931,findedEntity.getAttr2());
		
	}
	
	/**
	 * attr
	 */
	@Test 
	public void updateConstAttr(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		EasyQuery<TestEntity> e = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		EasyQuery<TestEntity> eq = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity finded = eq.eq(TEST, "1").getSingleResult();
		TestEntity entity = finded.clone();
		entity.setAttr2(CachableConst.TARGET_INT);
		pm.update(entity, new PersistenceHints().setFoundEntity(finded));
		
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		EasyQuery<TestEntity> e = ormQueryFactory.createEasyQuery(TestEntity.class);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);

		assertEquals(900,res.getAttr2());
	}
	
	
	/**
	 * ヒット件数等取征E
	 */
	@Test	
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setMaxResults(1);
		NativeResult result = query.getTotalResult();
		assertEquals(2,result.getHitCount());
		assertTrue(result.isLimited());
		assertEquals(1,result.getResultList().size());
	}
	
	/**
	 * ResultSetフェチE��取征E
	 */
	@Test
	public void lazySelect(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		long count = query.getFetchResult(new CallbackImpl());		
		assertEquals(2,count);
	}
	

	private class CallbackImpl implements QueryCallback<SampleNativeResult> {

		@Override
		public void handleRow(SampleNativeResult oneRecord, long rowIndex) {
			if(rowIndex == 0){
				assertEquals("2",oneRecord.getTest());
			}else {
				assertEquals("1",oneRecord.getTest());
			}
		}
		
	}
}

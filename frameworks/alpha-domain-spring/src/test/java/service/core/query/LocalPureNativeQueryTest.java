/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import service.test.CachableConst;
import service.test.SampleBatchUpdate;
import service.test.SampleNativeQuery;
import service.test.SampleNativeQueryConst;
import service.test.SampleNativeResult;
import service.test.SampleNativeUpdate;
import service.test.ServiceUnit;
import service.test.entity.ITestEntity;
import service.test.entity.TestEntity;
import client.sql.free.BatchModifyQuery;
import client.sql.free.BatchModifyQueryFactory;
import client.sql.free.HitData;
import client.sql.free.QueryCallback;
import client.sql.free.QueryFactory;
import client.sql.orm.EntityManagerImpl;
import client.sql.orm.CriteriaQueryFactory;
import client.sql.orm.CriteriaReadQuery;


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
	private CriteriaQueryFactory ormQueryFactory;

	@Autowired
	private EntityManagerImpl pm;
	
	@Autowired
	private BatchModifyQueryFactory buf;
	
	@Override
	protected EntityManager getEntityManager() {
		return pm;
	}
	
	/**
	 * @see alpha.domain.framework.test.ServiceUnit#setUpData(java.lang.String)
	 */
	@Override
	protected void setUpData(String dataPath){
		
		try{
			Connection con =pm.unwrap(Connection.class);
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
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
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
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
		query.setAttr("1000");
		query.setAttrs(Arrays.asList("1000","1000","1000"));
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
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
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
		pm.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(1);
		pm.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		pm.persist(t);
		
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);	
		query.setEntityManager(pm);
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(new Integer(1),result.get(0).getVersion());
		assertEquals("900",result.get(1).getAttr());
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createReadQuery(SampleNativeQueryConst.class,pm);
		c.setEntityManager(pm);
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
		SampleNativeQueryConst c = queryFactory.createReadQuery(SampleNativeQueryConst.class,pm);
		c.setEntityManager(pm);
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
		CriteriaReadQuery<TestEntity> eq = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity entity = eq.eq(TEST, "1").getSingleResult();
		TestEntity updatable = entity.clone();
		updatable.setAttr2(CachableConst.TARGET_INT);
		pm.merge(updatable, entity );
		
		SampleNativeQueryConst c = queryFactory.createReadQuery(SampleNativeQueryConst.class,pm);
		c.setEntityManager(pm);
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
		
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
		assertEquals(2,query.count());
	}
	
	/**
	 * test
	 */
	@Test 
	public void updateConstTest(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createModifyQuery(SampleNativeUpdate.class,pm);
		update.setEntityManager(pm);
		update.setTest("1");
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		CriteriaReadQuery<TestEntity> e = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity res = e.eq(TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * test
	 */
	@Test 
	public void batchUpdate(){
		setUpData("TEST.xls");
		
		BatchModifyQuery batcher = buf.createBatchUpdate();
		for(int i = 1  ; i <= 31; i++){
			SampleBatchUpdate update = queryFactory.createModifyQuery(SampleBatchUpdate.class,pm);
			update.setEntityManager(pm);
			update.setAttr("3");	
			update.setAttr2set(900+i);			
			batcher.addBatch(update);
		}
		int[] res= batcher.modify();
		assertEquals(31,res.length);
		for(int r : res){
			assertEquals(-2,r);
		}
		
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity findedEntity = query.eq(TEST, "1").getSingleResult();
		assertEquals(901,findedEntity.getAttr2());
		
	}
	
	/**
	 * attr
	 */
	@Test 
	public void updateConstAttr(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createModifyQuery(SampleNativeUpdate.class,pm);
		update.setEntityManager(pm);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		CriteriaReadQuery<TestEntity> e = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> eq = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity found = eq.eq(TEST, "1").getSingleResult();
		TestEntity entity = found.clone();
		entity.setAttr2(CachableConst.TARGET_INT);
		pm.merge(entity, found);
		
		SampleNativeUpdate update = queryFactory.createModifyQuery(SampleNativeUpdate.class,pm);
		update.setEntityManager(pm);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		CriteriaReadQuery<TestEntity> e = ormQueryFactory.createReadQuery(TestEntity.class,pm);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);

		assertEquals(900,res.getAttr2());
	}
	
	
	/**
	 * ヒット件数等取征E
	 */
	@Test	
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
		query.setMaxResults(1);
		HitData result = query.getTotalResult();
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
		
		SampleNativeQuery query = queryFactory.createReadQuery(SampleNativeQuery.class,pm);
		query.setEntityManager(pm);
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

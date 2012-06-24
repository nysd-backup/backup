/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import service.test.CachableConst;
import service.test.SampleNativeQuery;
import service.test.SampleNativeQueryConst;
import service.test.SampleNativeResult;
import service.test.SampleNativeUpdate;
import service.test.ServiceUnit;
import service.test.entity.ITestEntity;
import service.test.entity.TestEntity;
import client.sql.free.NativeResult;
import client.sql.free.QueryCallback;
import client.sql.free.QueryFactory;
import client.sql.orm.OrmQueryFactory;
import client.sql.orm.OrmSelect;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalNativeQueryTest extends ServiceUnit implements ITestEntity{
	
	@Resource
	private QueryFactory queryFactory;
	
	@Resource
	private OrmQueryFactory ormQueryFactory;
	
	@PersistenceContext(unitName="oracle")
	private EntityManager per;
	
	@Override
	protected EntityManager getEntityManager() {
		return per;
	}
	

	/**
	 * 通常検索
	 */
	@Test
	public void select(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);		
		query.setEntityManager(per);
		query.setTest("1");
		List<SampleNativeResult> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
				
	}
	
	/**
	 * 通常検索if刁E
	 */
	@Test
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
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
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
	}
	
	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
		query.setAttr2(500).setTest("1");
		SampleNativeResult e = query.getSingleResult();
		assertEquals("1",e.getTest());
	}
	
	/**
	 * setMaxSize
	 */
	@Test
	public void setMaxSize(){
		TestEntity entity = new TestEntity();
		entity.setTest("1000").setAttr("aa").setAttr2(111);
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per).setMaxResults(2);
		query.setEntityManager(per);
		List<SampleNativeResult> e = query.getResultList();
		assertEquals(2,e.size());
		e.get(0);
		e.get(1);
	}
	
	/**
	 * setFirstResult、E件目�E�E件目取征E
	 */
	@Test
	public void setFirstResult(){
		setUpData("TEST.xls");
		
		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		per.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.persist(t);
		per.flush();
		
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);	
		query.setEntityManager(per);
		query.setEntityManager(per);
		query.setFirstResult(1);
		query.setMaxResults(2);
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(new Integer(1),result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createSelect(SampleNativeQueryConst.class,per);
		c.setEntityManager(per);
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
		SampleNativeQueryConst c = queryFactory.createSelect(SampleNativeQueryConst.class,per);
		c.setEntityManager(per);
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
		OrmSelect<TestEntity> eq = ormQueryFactory.createSelect(TestEntity.class,per);		
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);
		per.flush();
		
		SampleNativeQueryConst c = queryFactory.createSelect(SampleNativeQueryConst.class,per);
		c.setEntityManager(per);
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
		
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
		query.setFirstResult(10).setMaxResults(100);
		assertEquals(2,query.count());
	}
	
	/**
	 * total result
	 */
	@Test
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
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
		
		SampleNativeQuery query = queryFactory.createSelect(SampleNativeQuery.class,per);
		query.setEntityManager(per);
		query.setMaxResults(100);
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

	/**
	 * test
	 */
	@Test 
	public void updateConstTest(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpsert(SampleNativeUpdate.class,per);
		update.setEntityManager(per);
		update.setTest("1");
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		OrmSelect<TestEntity> e = ormQueryFactory.createSelect(TestEntity.class,per);
		TestEntity res = e.eq(TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		
	}
	

	/**
	 * attr
	 */
	@Test 
	public void updateConstAttr(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpsert(SampleNativeUpdate.class,per);
		update.setEntityManager(per);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		OrmSelect<TestEntity> e = ormQueryFactory.createSelect(TestEntity.class,per);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		OrmSelect<TestEntity> eq = ormQueryFactory.createSelect(TestEntity.class,per);
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNativeUpdate update = queryFactory.createUpsert(SampleNativeUpdate.class,per);
		update.setEntityManager(per);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		OrmSelect<TestEntity> e = ormQueryFactory.createSelect(TestEntity.class,per);

		//NativeUpdateを実行しても永続化コンチE��スト�E実行されなぁE��従って最初に検索した永続化コンチE��スト�EのエンチE��チE��が�E利用される、E
		//これを防ぎ、NamedUpdateの実行結果を反映したDB値を取得するためにrefleshする、E
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);

		assertEquals(900,res.getAttr2());
	}

}

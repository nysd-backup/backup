/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import kosmos.framework.core.exception.UnexpectedNoDataFoundException;
import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.test.CachableConst;
import kosmos.framework.service.test.SampleNativeQuery;
import kosmos.framework.service.test.SampleNativeQueryConst;
import kosmos.framework.service.test.SampleNativeResult;
import kosmos.framework.service.test.SampleNativeUpdate;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.ITestEntity;
import kosmos.framework.service.test.entity.TestEntity;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
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
public class LocalNativeQueryTest extends ServiceUnit implements ITestEntity{
	
	@Resource
	private QueryFactory queryFactory;
	
	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;
	
	@Autowired
	private EntityManagerProvider per;

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
	 * 結果0件シスチE��エラー
	 */
	@Test
	public void nodataError(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class).enableNoDataError();
		query.setAttr2(500).setTest("1").setArc("500");
		
		try{
			query.getResultList();
			fail();
		}catch(UnexpectedNoDataFoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * exists
	 */
	@Test
	public void exists(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1");
		assertTrue(query.exists());
	}

//	/**
//	 * exists
//	 */
//	@Test
//	public void existsMessage(){
//		
//		assertFalse(MessageContext.getCurrentInstance().isRollbackOnly());
//		
//		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
//		query.setTest("200");
//		assertFalse(query.exists(mh.createMessage(1,"test")));
//		
//		assertTrue(MessageContext.getCurrentInstance().isRollbackOnly());
//		assertEquals(1,MessageContext.getCurrentInstance().getMessageList().size());		
//		
//	}
//	
//	/**
//	 * exists
//	 */
//	@Test
//	public void throwIfEmpty(){
//		
//		assertFalse(MessageContext.getCurrentInstance().isRollbackOnly());
//
//		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
//		query.setAttr2(500).setTest("200");
//		query.throwIfExists(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfEmpty(mh.createMessage(1, "test"));
//			fail();
//		}catch(NoDataFoundException se){		
//			assertTrue(MessageContext.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,MessageContext.getCurrentInstance().getMessageList().size());
//		}
//	}
//	
//	/**
//	 * exists
//	 */
//	@Test
//	public void throwIfExists(){
//		
//		setUpData("TEST.xls");
//		assertFalse(MessageContext.getCurrentInstance().isRollbackOnly());
//
//		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
//		query.setAttr2(500).setTest("1");
//		query.throwIfEmpty(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfExists(mh.createMessage(1, "test"));
//			fail();
//		}catch(DuplicateDataFoundException se){		
//			assertTrue(MessageContext.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,MessageContext.getCurrentInstance().getMessageList().size());
//		}
//	}
	
	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
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
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class).setMaxResults(2);
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
		per.getEntityManager().persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		per.getEntityManager().persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.getEntityManager().persist(t);
		per.getEntityManager().flush();
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);		
		query.setFirstResult(1);
		query.setMaxResults(2);
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
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
		StrictQuery<TestEntity> eq = ormQueryFactory.createStrictQuery(TestEntity.class);
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);
		per.getEntityManager().flush();
		
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
		query.setFirstResult(10).setMaxResults(100);
		assertEquals(2,query.count());
	}
	
	/**
	 * total result
	 */
	@Test
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setMaxResults(1);
		NativeResult<SampleNativeResult> result = query.getTotalResult();
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
		query.setMaxResults(100);
		List<SampleNativeResult> e = query.getFetchResult();
		
		Iterator<SampleNativeResult> itr = e.iterator();
		int cnt = 0;
		while(itr.hasNext()){
			SampleNativeResult next = itr.next();
			if(cnt == 0){
				assertEquals("2",next.getTest());
			}else {
				assertEquals("1",next.getTest());
			}
			cnt++;
		}
		assertEquals(2,cnt);
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
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity res = e.eq(TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		
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
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> eq = ormQueryFactory.createStrictQuery(TestEntity.class);
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		
		//NativeUpdateを実行しても永続化コンチE��スト�E実行されなぁE��従って最初に検索した永続化コンチE��スト�EのエンチE��チE��が�E利用される、E
		//これを防ぎ、NamedUpdateの実行結果を反映したDB値を取得するためにrefleshする、E
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		TestEntity res = e.eq(ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);

		assertEquals(900,res.getAttr2());
	}

}

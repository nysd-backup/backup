/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.testcase;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.service.core.CachableConst;
import kosmos.framework.service.core.activation.AbstractServiceLocator;
import kosmos.framework.service.core.entity.ITestEntity;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.service.core.query.SampleNativeQuery;
import kosmos.framework.service.core.query.SampleNativeQueryConst;
import kosmos.framework.service.core.query.SampleNativeResult;
import kosmos.framework.service.core.query.SampleNativeUpdate;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;



/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class LocalNativeQueryTestBean extends BaseCase{
	
	private QueryFactory queryFactory = null;
	
	private AdvancedOrmQueryFactory ormQueryFactory = null;
	
	@PostConstruct
	public void construct(){
		queryFactory = AbstractServiceLocator.createDefaultQueryFactory();
		ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
	}
	/**
	 * 通常検索
	 */
	public void select(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);		
		query.setTest("1");
		List<SampleNativeResult> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		context.setRollbackOnly();	
	}
	/**
	 * 通常検索if刁E
	 */
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr("1000");
		query.setTest("1");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
		context.setRollbackOnly();	
	}
	

	/**
	 * if斁E��索
	 * 数値比輁E��not null、文字�E比輁E
	 */
	
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
		context.setRollbackOnly();	
	}
	
//	/**
//	 * 結果0件シスチE��エラー
//	 */
//	
//	public void nodataError(){
//		setUpData("TEST.xls");
//		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class).enableNoDataError();
//		query.setAttr2(500).setTest("1").setArc("500");
//		
//		try{
//			query.getResultList();
//			fail();
//		}catch(UnexpectedNoDataFoundException e){
//			e.printStackTrace();
//		}
//		context.setRollbackOnly();	
//	}
	
//	/**
//	 * exists
//	 */
//	
//	public void exists(){
//		setUpData("TEST.xls");
//		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
//		query.setAttr2(500).setTest("1");
//		assertTrue(query.exists());
//		context.setRollbackOnly();	
//	}

	/**
	 * getSingleResult
	 */	
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1");
		SampleNativeResult e = query.getSingleResult();
		assertEquals("1",e.getTest());
		context.setRollbackOnly();	
	}
	
	/**
	 * setMaxSize
	 */
	
	public void setMaxSize(){
		TestEntity entity = new TestEntity();
		entity.setTest("1000").setAttr("aa").setAttr2(111);
		setUpData("TEST.xls");
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class).setMaxResults(2);
		List<SampleNativeResult> e = query.getResultList();
		assertEquals(2,e.size());
		e.get(0);
		e.get(1);
		context.setRollbackOnly();	
	}
	
	/**
	 * setFirstResult、E件目�E�E件目取征E
	 */
	
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
		context.setRollbackOnly();	
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setTest("1");
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void constAttr(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setTest("2");
		c.setAttr(CachableConst.TARGET_TEST_1_OK);
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void constVersionNo(){
	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> eq = ormQueryFactory.createStrictQuery(TestEntity.class);
		eq.eq(ITestEntity.TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);
		per.getEntityManager().flush();
		
		SampleNativeQueryConst c = queryFactory.createQuery(SampleNativeQueryConst.class);
		c.setArc(CachableConst.TARGET_INT);		
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}
	
	/**
	 * ヒット件数等取征E
	 */
	
	public void count(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setFirstResult(10).setMaxResults(100);
		assertEquals(2,query.count());
		context.setRollbackOnly();	
	}
	
	/**
	 * total result
	 */
	
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = queryFactory.createQuery(SampleNativeQuery.class);
		query.setMaxResults(1);
		NativeResult result = query.getTotalResult();
		assertEquals(2,result.getHitCount());
		assertTrue(result.isLimited());
		assertEquals(1,result.getResultList().size());
		context.setRollbackOnly();	
	}
	
	/**
	 * ResultSetフェチE��取征E
	 */
	
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
		context.setRollbackOnly();	
	}
	

	/**
	 * test
	 */
	 
	public void updateConstTest(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setTest("1");
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity res = e.eq(ITestEntity.TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
		
	}
	

	/**
	 * attr
	 */
	 
	public void updateConstAttr(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity res = e.eq(ITestEntity.ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> eq = ormQueryFactory.createStrictQuery(TestEntity.class);
		eq.eq(ITestEntity.TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNativeUpdate update = queryFactory.createUpdate(SampleNativeUpdate.class);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<TestEntity> e = ormQueryFactory.createStrictQuery(TestEntity.class);
		
		//NativeUpdateを実行しても永続化コンチE��スト�E実行されなぁE��従って最初に検索した永続化コンチE��スト�EのエンチE��チE��が�E利用される、E
		//これを防ぎ、NamedUpdateの実行結果を反映したDB値を取得するためにrefleshする、E
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		TestEntity res = e.eq(ITestEntity.ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);

		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
	}

}

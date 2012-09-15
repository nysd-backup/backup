/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import alpha.sqlclient.free.QueryFactory;
import alpha.sqlclient.orm.CriteriaQueryFactory;
import alpha.sqlclient.orm.CriteriaReadQuery;

import service.test.CachableConst;
import service.test.SampleNamedQuery;
import service.test.SampleNamedQueryConst;
import service.test.SampleNamedUpdate;
import service.test.ServiceUnit;
import service.test.entity.DateEntity;
import service.test.entity.IDateEntity;
import service.test.entity.ITestEntity;
import service.test.entity.TestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalNamedQueryTest extends ServiceUnit implements ITestEntity{
	
	@Resource
	private QueryFactory queryFactory;
	
	@Resource
	private CriteriaQueryFactory ormQueryFactory;
	
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
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
		query.setEntityManager(per);
		query.setHint(QueryHints.HINT,"/*+ hint */");
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		per.detach(result.get(0));	
		result.get(0).setAttr("500");	

		TestEntity results =query.getSingleResult();
		assertEquals("3",results.getAttr());
	}
	
	/**
	 * 通常検索if刁E
	 */
	@Test
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
		query.setEntityManager(per);
		query.setAttr("1000");
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	

	/**
	 * if斁E��索
	 * 数値比輁E��not null、文字�E比輁E
	 */
	@Test
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
		query.setEntityManager(per);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	
//	/**
//	 * 結果0件シスチE��エラー
//	 */
//	@Test
//	public void nodataError(){
//		setUpData("TEST.xls");
//		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class).enableNoDataError();
//		query.setAttr2(500).setTest("1").setArc("500");
//		
//		try{
//			query.getResultList();
//			fail();
//		}catch(UnexpectedNoDataFoundException e){
//			e.printStackTrace();
//		}
//	}
	
//	/**
//	 * exists
//	 */
//	@Test
//	public void exists(){
//		setUpData("TEST.xls");
//		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
//		query.setAttr2(500).setTest("1");
//		assertTrue(query.exists());
//	}

//	/**
//	 * exists
//	 */
//	@Test
//	public void existsMessage(){
//		
//		assertFalse(MessageLevel.getCurrentInstance().isRollbackOnly());
//		
//		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
//		query.setTest("200");
//		assertFalse(query.exists(mh.createMessage(1,"test")));
//		
//		assertTrue(MessageLevel.getCurrentInstance().isRollbackOnly());
//		assertEquals(1,MessageLevel.getCurrentInstance().getMessageList().size());		
//		
//	}
//	
//	/**
//	 * exists
//	 */
//	@Test
//	public void throwIfEmpty(){
//		
//		assertFalse(MessageLevel.getCurrentInstance().isRollbackOnly());
//
//		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
//		query.setAttr2(500).setTest("200");
//		query.throwIfExists(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfEmpty(mh.createMessage(1, "test"));
//			fail();
//		}catch(UnexpectedNoDataFoundException se){		
//			assertTrue(MessageLevel.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,MessageLevel.getCurrentInstance().getMessageList().size());
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
//		assertFalse(MessageLevel.getCurrentInstance().isRollbackOnly());
//
//		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
//		query.setAttr2(500).setTest("1");
//		query.throwIfEmpty(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfExists(mh.createMessage(1, "test"));
//			fail();
//		}catch(DuplicateDataFoundException se){		
//			assertTrue(MessageLevel.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,MessageLevel.getCurrentInstance().getMessageList().size());
//		}
//	}
	
	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
		query.setEntityManager(per);
		query.setAttr2(500).setTest("1");
		TestEntity e = query.getSingleResult();
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
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per).setMaxResults(2);
		query.setEntityManager(per);
		List<TestEntity> e = query.getResultList();
		assertEquals(2,e.size());
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
		
		SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);		
		query.setEntityManager(per);
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
		
		//更新
		result.get(0).setAttr("AAA");
		per.flush();
		
		//楽観ロチE��番号インクリメント確誁E
		result = query.getResultList();		
		assertEquals(2,result.get(0).getVersion());
	}
	
	/**
	 * setLockMode firstSize、maxSize持E��不可能
	 */
	@Test
	public void setLockMode() throws Exception{ 
//		setUpData("TEST.xls");
//		
//		EntityManager em = ServiceLocator.lookupDefault(EntityManagerProvider.class).getEntityManager();		
//		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();
//		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
//		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
//		Connection con = accessor.getConnection();
//		con.createStatement().execute("SELECT * FROM TESTA WHERE TEST = '1' FOR UPDATE NOWAIT");
//		
//		try{
//			SampleNamedQuery query = queryFactory.createReadQuery(SampleNamedQuery.class,per);
//			query.setTest("1");
//			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint("javax.persistence.lock.timeout", "0");
//			query.getResultList();
//			fail();
//		}catch(PessimisticLockException ple){	
//			ple.printStackTrace();
//			SQLException ex = (SQLException)ple.getCause().getCause();
//			assertEquals(54,ex.getErrorCode());
//		}
		//TODO 
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNamedQueryConst c = queryFactory.createReadQuery(SampleNamedQueryConst.class,per);
		c.setEntityManager(per);
		c.setTest("1");
		List<TestEntity> e = c.getResultList();
		assertEquals(1,e.size());
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constAttr(){
	
		setUpData("TEST.xls");
		SampleNamedQueryConst c = queryFactory.createReadQuery(SampleNamedQueryConst.class,per);
		c.setEntityManager(per);
		c.setTest("2");
		c.setAttr(CachableConst.TARGET_TEST_1_OK);
		List<TestEntity> e = c.getResultList();
		assertEquals(1,e.size());
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constVersionNo(){
	
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> eq = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);
		
		SampleNamedQueryConst c = queryFactory.createReadQuery(SampleNamedQueryConst.class,per);
		c.setEntityManager(per);
		c.setArc(CachableConst.TARGET_INT);		
		List<TestEntity> e = c.getResultList();
		assertEquals(1,e.size());
	}
	
	/**
	 * test
	 */
	@Test 
	@Rollback(false)
	public void updateConstTest(){
	
		DateEntity e2 = new DateEntity();
		e2.setAttr("aa").setTest("1");
		per.persist(e2);
		
		SampleNamedUpdate update = queryFactory.createModifyQuery(SampleNamedUpdate.class,per);//.setProviderHint(QueryHints.MAINTAIN_CACHE, HintValues.TRUE);		
		update.setTest("1");
		update.setDateCol(new Date());
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);

		//e2が永続化コンチE��ストに入ったままなので、JPQLアチE�EチE�Eトを実行する�Eで更新
		CriteriaReadQuery<DateEntity> e = ormQueryFactory.createReadQuery(DateEntity.class,per);
		
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		DateEntity res = e.eq(IDateEntity.TEST, "1").getSingleResult();
		assertEquals(900,res.getAttr2());
		
	}
	

	/**
	 * attr
	 */
	@Test 
	public void updateConstAttr(){
		DateEntity e2 = new DateEntity();
		e2.setAttr(CachableConst.TARGET_TEST_1_OK).setTest("2");
		per.persist(e2);
		
		SampleNamedUpdate update = queryFactory.createModifyQuery(SampleNamedUpdate.class,per);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		update.setDateCol(new Date());
		int count = update.update();
		assertEquals(1,count);
		
		CriteriaReadQuery<DateEntity> e = ormQueryFactory.createReadQuery(DateEntity.class,per);
		
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		DateEntity res = e.eq(IDateEntity.ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
	
		DateEntity e2 = new DateEntity();
		e2.setAttr(CachableConst.TARGET_TEST_1_OK).setTest("2").setDateCol(new Date());
		per.persist(e2);
		
		CriteriaReadQuery<DateEntity> eq = ormQueryFactory.createReadQuery(DateEntity.class,per);
		
		eq.eq(IDateEntity.TEST, "2").getSingleResult().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNamedUpdate update = queryFactory.createModifyQuery(SampleNamedUpdate.class,per);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		update.setDateCol(new Date());
		int count = update.update();
		assertEquals(1,count);
		
		CriteriaReadQuery<DateEntity> e = ormQueryFactory.createReadQuery(DateEntity.class,per);
		
		//NamedUpdateを実行しても永続化コンチE��スト�E実行されなぁE��従って最初に検索した永続化コンチE��スト�EのエンチE��チE��が�E利用される、E
		//これを防ぎ、NamedUpdateの実行結果を反映したDB値を取得するためにrefleshする、E
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		DateEntity res = e.eq(IDateEntity.ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
	}

}

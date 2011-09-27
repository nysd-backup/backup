/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.core.exception.system.UnexpectedNoDataFoundException;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.test.CachableConst;
import framework.service.test.SampleNamedQuery;
import framework.service.test.SampleNamedQueryConst;
import framework.service.test.SampleNamedUpdate;
import framework.service.test.ServiceUnit;
import framework.service.test.entity.DateEntity;
import framework.service.test.entity.IDateEntity;
import framework.service.test.entity.ITestEntity;
import framework.service.test.entity.TestEntity;
import framework.sqlclient.api.free.QueryFactory;

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
	private AdvancedOrmQueryFactory ormQueryFactory;
	
	@Autowired
	private EntityManagerAccessor per;
	
	/**
	 * 騾壼ｸｸ讀懃ｴ｢
	 */
	@Test
	public void select(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
		query.setHintString("/*+ hint */");
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		per.detach(result.get(0));	
		result.get(0).setAttr("500");	

		TestEntity results =query.getSingleResult();
		assertEquals("3",results.getAttr());
	}
	
	/**
	 * 騾壼ｸｸ讀懃ｴ｢if蛻・
	 */
	@Test
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
		query.setAttr("1000");
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	

	/**
	 * if譁・､懃ｴ｢
	 * 謨ｰ蛟､豈碑ｼ・］ot null縲∵枚蟄怜・豈碑ｼ・
	 */
	@Test
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	
	/**
	 * 邨先棡0莉ｶ繧ｷ繧ｹ繝・Β繧ｨ繝ｩ繝ｼ
	 */
	@Test
	public void nodataError(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class).enableNoDataError();
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
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
		query.setAttr2(500).setTest("1");
		assertTrue(query.exists());
	}

//	/**
//	 * exists
//	 */
//	@Test
//	public void existsMessage(){
//		
//		assertFalse(ServiceContext.getCurrentInstance().isRollbackOnly());
//		
//		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
//		query.setTest("200");
//		assertFalse(query.exists(mh.createMessage(1,"test")));
//		
//		assertTrue(ServiceContext.getCurrentInstance().isRollbackOnly());
//		assertEquals(1,ServiceContext.getCurrentInstance().getMessageList().size());		
//		
//	}
//	
//	/**
//	 * exists
//	 */
//	@Test
//	public void throwIfEmpty(){
//		
//		assertFalse(ServiceContext.getCurrentInstance().isRollbackOnly());
//
//		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
//		query.setAttr2(500).setTest("200");
//		query.throwIfExists(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfEmpty(mh.createMessage(1, "test"));
//			fail();
//		}catch(NoDataFoundException se){		
//			assertTrue(ServiceContext.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,ServiceContext.getCurrentInstance().getMessageList().size());
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
//		assertFalse(ServiceContext.getCurrentInstance().isRollbackOnly());
//
//		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
//		query.setAttr2(500).setTest("1");
//		query.throwIfEmpty(mh.createMessage(1, "test"));
//		
//		try{
//			query.throwIfExists(mh.createMessage(1, "test"));
//			fail();
//		}catch(DuplicateDataFoundException se){		
//			assertTrue(ServiceContext.getCurrentInstance().isRollbackOnly());
//			assertEquals(1,ServiceContext.getCurrentInstance().getMessageList().size());
//		}
//	}
	
	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
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
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class).setMaxResults(2);
		List<TestEntity> e = query.getResultList();
		assertEquals(2,e.size());
	}
	
	/**
	 * setFirstResult縲・莉ｶ逶ｮ・・莉ｶ逶ｮ蜿門ｾ・
	 */
	@Test
	public void setFirstResult(){
		setUpData("TEST.xls");
	
		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNo縺ｮ謖・ｮ壹・辟｡隕悶＆繧後ｋ
		per.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.persist(t);
		
		SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);		
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//蠢・★讌ｽ隕ｳ繝ｭ繝・け逡ｪ蜿ｷ縺ｯ1縺九ｉinsert
		assertEquals("900",result.get(1).getAttr());
		
		//譖ｴ譁ｰ
		result.get(0).setAttr("AAA");
		per.flush();
		
		//讌ｽ隕ｳ繝ｭ繝・け逡ｪ蜿ｷ繧､繝ｳ繧ｯ繝ｪ繝｡繝ｳ繝育｢ｺ隱・
		result = query.getResultList();		
		assertEquals(2,result.get(0).getVersion());
	}
	
	/**
	 * setLockMode firstSize縲［axSize謖・ｮ壻ｸ榊庄閭ｽ
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
//			SampleNamedQuery query = queryFactory.createQuery(SampleNamedQuery.class);
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
		SampleNamedQueryConst c = queryFactory.createQuery(SampleNamedQueryConst.class);
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
		SampleNamedQueryConst c = queryFactory.createQuery(SampleNamedQueryConst.class);
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
		StrictQuery<TestEntity> eq = ormQueryFactory.createStrictQuery(TestEntity.class);
		eq.eq(TEST, "1").getSingleResult().setAttr2(CachableConst.TARGET_INT);
		
		SampleNamedQueryConst c = queryFactory.createQuery(SampleNamedQueryConst.class);
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
		
		SampleNamedUpdate update = queryFactory.createUpdate(SampleNamedUpdate.class);//.setProviderHint(QueryHints.MAINTAIN_CACHE, HintValues.TRUE);
		update.setTest("1");
		update.setDateCol(new Date());
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);

		//e2縺梧ｰｸ邯壼喧繧ｳ繝ｳ繝・く繧ｹ繝医↓蜈･縺｣縺溘∪縺ｾ縺ｪ縺ｮ縺ｧ縲゛PQL繧｢繝・・繝・・繝医ｒ螳溯｡後☆繧九・縺ｧ譖ｴ譁ｰ
		StrictQuery<DateEntity> e = ormQueryFactory.createStrictQuery(DateEntity.class);
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
		
		SampleNamedUpdate update = queryFactory.createUpdate(SampleNamedUpdate.class);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		update.setDateCol(new Date());
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<DateEntity> e = ormQueryFactory.createStrictQuery(DateEntity.class);
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
		
		StrictQuery<DateEntity> eq = ormQueryFactory.createStrictQuery(DateEntity.class);
		eq.eq(IDateEntity.TEST, "2").getSingleResult().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNamedUpdate update = queryFactory.createUpdate(SampleNamedUpdate.class);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		update.setDateCol(new Date());
		int count = update.update();
		assertEquals(1,count);
		
		StrictQuery<DateEntity> e = ormQueryFactory.createStrictQuery(DateEntity.class);
		
		//NamedUpdate繧貞ｮ溯｡後＠縺ｦ繧よｰｸ邯壼喧繧ｳ繝ｳ繝・く繧ｹ繝医・螳溯｡後＆繧後↑縺・ょｾ薙▲縺ｦ譛蛻昴↓讀懃ｴ｢縺励◆豌ｸ邯壼喧繧ｳ繝ｳ繝・く繧ｹ繝亥・縺ｮ繧ｨ繝ｳ繝・ぅ繝・ぅ縺悟・蛻ｩ逕ｨ縺輔ｌ繧九・
		//縺薙ｌ繧帝亟縺弱¨amedUpdate縺ｮ螳溯｡檎ｵ先棡繧貞渚譏縺励◆DB蛟､繧貞叙蠕励☆繧九◆繧√↓reflesh縺吶ｋ縲・
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		DateEntity res = e.eq(IDateEntity.ATTR, CachableConst.TARGET_TEST_1).getResultList().get(0);
		assertEquals(900,res.getAttr2());
	}

}

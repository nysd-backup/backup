/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.RollbackException;

import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionSystemException;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.EasyQuery;
import framework.api.query.orm.EasyUpdate;
import framework.api.query.orm.StrictQuery;
import framework.api.query.orm.StrictUpdate;
import framework.core.exception.system.UnexpectedMultiResultException;
import framework.core.exception.system.UnexpectedNoDataFoundException;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.ServiceContextImpl;
import framework.service.test.RequiresNewReadOnlyService;
import framework.service.test.RequiresNewService;
import framework.service.test.ServiceTestContextImpl;
import framework.service.test.ServiceUnit;
import framework.service.test.entity.ChildEntity;
import framework.service.test.entity.DateEntity;
import framework.service.test.entity.IDateEntity;
import framework.service.test.entity.ITestEntity;
import framework.service.test.entity.ParentEntity;
import framework.service.test.entity.TestEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalEntityQueryTest extends ServiceUnit implements ITestEntity{

	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;

	@Autowired
	private EntityManagerAccessor per;
	
	/**
	 * 譚｡莉ｶ霑ｽ蜉
	 * @throws SQLException 
	 */
	@Test
	public void allCondition() throws SQLException{	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);	
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		TestEntity first = result.get(0);
		per.detach(first);
		first.setAttr("100");
		
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
		List<TestEntity> updated = getOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
	}
	
	/**
	 * 繝・ぅ繧ｿ繝・メ
	 */
	@Test
	public void disableDetach(){
		setUpData("TEST.xls");
		//譖ｴ譁ｰ蜑榊叙蠕・
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//譖ｴ譁ｰ
		TestEntity first = result.get(0);
		first.setAttr("100");
	
		//譖ｴ譁ｰ邨先棡
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity entity = forres.eq(TEST, "2").getSingleResult();
		assertEquals("100",entity.getAttr());
	}
	
	/**
	 * 譖ｴ譁ｰ蠕梧､懃ｴ｢
	 */
	@Test
	public void updateAny(){
		
		setUpData("TEST.xls");
		
		//譖ｴ譁ｰ
		StrictUpdate<TestEntity> update = ormQueryFactory.createStrictUpdate(TestEntity.class);
		update.eq(TEST, "2").set(ATTR, "AAA").update();
		
		//讀懃ｴ｢
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		
		//譖ｴ譁ｰ邨先棡(NamedUpdate譖ｴ譁ｰ蜑阪↓讀懃ｴ｢縺励※縺・ｌ縺ｰ豌ｸ邯壼喧繧ｳ繝ｳ繝・く繧ｹ繝医・譖ｴ譁ｰ蜑阪く繝｣繝・す繝･縺御ｽｿ逕ｨ縺輔ｌ繧九◆繧〉eflesh縺吶ｋ蠢・ｦ√≠繧翫ゆｻ雁屓縺ｯNamedUpdate螳溯｡後＠縺ｦ縺・↑縺・・縺ｧreflesh荳崎ｦ・ｼ・
		TestEntity entity = query.eq(TEST, "2").getSingleResult();
		assertEquals("AAA",entity.getAttr());
	}
	
	/**
	 * 1莉ｶ蜿門ｾ励髯埼・た繝ｼ繝・
	 */
	@Test
	public void getSingleResultWithDesc(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
	}
	
	/**
	 * 1莉ｶ蜿門ｾ励譏・・た繝ｼ繝・
	 */
	@Test
	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).asc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	

	/**
	 * 2莉ｶ逶ｮ蜿門ｾ・
	 */
	@Test
	public void getSingleResultSetFirstWithDesc(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(TEST);
		query.setFirstResult(1);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	
	/**
	 * 2莉ｶ逶ｮ縺九ｉ蜿門ｾ・
	 */
	@Test
	public void getResultSetFirst(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.setFirstResult(1);
		List<TestEntity> result = query.getResultList();
		assertEquals(1,result.size());
		assertEquals("2",result.get(0).getAttr());
	}
	
	/**
	 * 2莉ｶ逶ｮ縺九ｉ3莉ｶ逶ｮ蜿門ｾ・
	 */
	@Test
	public void getResultSetFirstMax2(){
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
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(TEST);
		query.contains(TEST, "0","1,","2","900","901","902");
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
	 * 0莉ｶ繧ｷ繧ｹ繝・Β繧ｨ繝ｩ繝ｼ
	 */
	@Test
	public void nodataError(){
		try{
			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).enableNoDataError();
			query.eq(TEST, "AGA").getSingleResult();
			fail();
		}catch(UnexpectedNoDataFoundException une){
			une.printStackTrace();
		}catch(Throwable t){
			fail();
		}
	}
	
	/**
	 * PK讀懃ｴ｢
	 */
	@Test
	public void find(){	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		per.detach(result);
		result.setAttr("1100");
		
		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("3",result.getAttr());
		
	}
	
	/**
	 * PK讀懃ｴ｢縲∵峩譁ｰ
	 */
	@Test
	public void findDisableDetach(){
	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		result.setAttr("1100");
		
		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("1100",result.getAttr());
	}

	/**
	 * 0莉ｶ繧ｷ繧ｹ繝・Β繧ｨ繝ｩ繝ｼ
	 */
	@Test
	public void findNodataError(){
		try{
			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
			query.enableNoDataError();
			query.find("AA");
			fail();
		}catch(UnexpectedNoDataFoundException une){
			une.printStackTrace();
		}
	}
	/**
	 * 譚｡莉ｶ霑ｽ蜉
	 */
	@Test
	public void findAny(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).eq(TEST,"1");
		TestEntity result = query.findAny();
		per.detach(result);
		result.setAttr("test");
		result = query.findAny();
		assertEquals("3",result.getAttr());
	}
	
	/**
	 * 譚｡莉ｶ霑ｽ蜉 譖ｴ譁ｰ
	 */
	@Test
	public void findAnyDisableDetach(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).eq(TEST,"1");
		TestEntity result = query.findAny();
		result.setAttr("test");
		result = query.findAny();
		assertEquals("test",result.getAttr());
	}
	
	/**
	 * 0莉ｶ繧ｷ繧ｹ繝・Β繧ｨ繝ｩ繝ｼ
	 */
	@Test
	public void findAnyNodataError(){
		try{
			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
			query.enableNoDataError();
			query.eq(TEST, "aaa");
			query.findAny();
			fail();
		}catch(UnexpectedNoDataFoundException une){
			une.printStackTrace();
		}catch(Throwable t){
			fail();
		}
	}
	

	/**
	 * ANY隍・焚莉ｶ蟄伜惠
	 */
	@Test
	public void findAnyMultiResultError(){
		setUpData("TEST.xls");
		try{
			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
			query.findAny();
			fail();
		}catch(UnexpectedMultiResultException umre){
			umre.printStackTrace();
		}catch(Throwable t){
			t.printStackTrace();
			fail();
		}
	}

	
	/**
	 *  蟄伜惠繝√ぉ繝・け not 
	 */
	@Test
	public void exists(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		assertTrue(query.exists());
	}
	
	/**
	 * PK蟄伜惠繝√ぉ繝・け
	 */
	@Test
	public void isEmptyPK(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertFalse(query.exists("AGA"));
	}
	
	/**
	 * PK蟄伜惠繝√ぉ繝・け
	 */
	@Test
	public void existsPK(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertTrue(query.exists("1"));
	}
	
	/**
	 * ANY蟄伜惠繝√ぉ繝・け 
	 */
	@Test
	public void existsByAny(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		query.eq(TEST, "1");
		assertTrue(query.existsByAny());
	}
	
	/**
	 * ANY隍・焚莉ｶ蟄伜惠繝√ぉ繝・け
	 */
	@Test
	public void existsByAnyMultiResultError(){
		setUpData("TEST.xls");
		try{
			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
			query.existsByAny();
			fail();
		}catch(UnexpectedMultiResultException umre){
			umre.printStackTrace();
		}
	}
	
	/**
	 * 荳諢丞宛邏・お繝ｩ繝ｼ
	 */
	@Test	
	public void uniqueConstraintError(){
		setUpData("TEST.xls");
	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		per.persist(entity);
		
		try{
			per.flush();
			fail();
		}catch(PersistenceException de){
			SQLIntegrityConstraintViolationException sqle = (SQLIntegrityConstraintViolationException)de.getCause().getCause();
			assertEquals("1",String.valueOf(sqle.getErrorCode()));
		}
	
	}
	
	/**
	 * 荳諢丞宛邏・お繝ｩ繝ｼ辟｡隕・
	 */
	@Test	
	public void ignoreUniqueConstraintError(){
		
		//荳諢丞宛邏・┌蜉ｹ蛹・
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpData("TEST.xls");
	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		per.persist(entity);	
		per.flush();
		
		impl.setValidOptimisticLockError();
		
		TestEntity entity2 = new TestEntity();
		entity2.setTest("530");
		entity2.setAttr("aaa");
		entity2.setAttr2(30);
	
		per.persist(entity2);	

	}
	
	/**
	 * 繝ｭ繝・け騾｣逡ｪ繝√ぉ繝・け繧ｨ繝ｩ繝ｼ
	 */
	@Test	
	public void versionNoError(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		final TestEntity result = query.find("1");
		result.setVersion(2);
		try{
			per.flush();
			fail();
		}catch(OptimisticLockException e){
			return;
		}
	}
	/**
	 * 繝ｭ繝・け騾｣逡ｪ繝√ぉ繝・け繧ｨ繝ｩ繝ｼ辟｡隕・
	 */
	@Test	
	public void ignoreVersionNoError(){
		
		//繝ｭ繝・け騾｣逡ｪ繧ｨ繝ｩ繝ｼ辟｡蜉ｹ蛹・陦悟腰菴阪・譖ｴ譁ｰ繧偵＆縺帙ｋ蝣ｴ蜷医√％縺・☆繧九°閾ｪ蠕九ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺ｫ縺吶ｋ蠢・ｦ√′縺ゅｋ・・
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		final TestEntity result = query.find("1");
		result.setVersion(2);
		
		
		//繝ｭ繝・け騾｣逡ｪ繧ｨ繝ｩ繝ｼ辟｡隕・DumyExceptionHandler縺ｧ謠｡繧翫▽縺ｶ縺暦ｼ・
		per.flush(
//				new FlushHandler(){
//
//			@Override
//			public void handle(RuntimeException pe) {
//				pe.printStackTrace();
//				assertEquals(OptimisticLockException.class,pe.getClass());		
//				//繝ｪ繝輔Ξ繝・す繝･縺励※繝ｭ繝・け騾｣逡ｪ繧奪B縺ｨ蜷医ｏ縺帙※縺翫°縺ｪ縺・→谺｡縺ｮFlush縺ｧ繧ょ､ｱ謨励＠縺ｦ縺励∪縺・・
//				per.reflesh(result);
//			}
//			
//		}
				);
		impl.setValidOptimisticLockError();
		
		//繝舌・繧ｸ繝ｧ繝ｳ逡ｪ蜿ｷ繧呈欠螳壹＠縺ｪ縺・◆繧∵峩譁ｰ謌仙粥
		TestEntity res2 = query.find("2");
		assertEquals(0,res2.getVersion());
		res2.setAttr("aa");
		per.flush();

		assertEquals("aa", query.find("2").getAttr());
	}
	
	/**
	 * 謔ｲ隕ｳ繝ｭ繝・け繧ｨ繝ｩ繝ｼ辟｡蜉ｹ蛹・
	 * @throws SQLException 
	 */
	@Test
	public void invalidFindWithLockNoWaitError(){	
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpDataForceCommit("TEST.xls");
		
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead().find("1");
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		assertEquals("OK",service.test());	
		
	}
	
	/**
	 * 謔ｲ隕ｳ繝ｭ繝・け繧ｨ繝ｩ繝ｼ
	 * @throws SQLException 
	 */
	@Test
	public void findWithLockNoWaitError(){	
		
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead().find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			//繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｧ繧ゅせ繝ｭ繝ｼ縺輔ｌ縺滉ｾ句､悶・縺昴・縺ｾ縺ｾ繧ｭ繝｣繝・メ蜿ｯ閭ｽ
			service.test();
			fail();
		}catch(PessimisticLockException pe){
			SQLException sqle = (SQLException)pe.getCause().getCause();
			assertEquals("54",String.valueOf(sqle.getErrorCode()));
		}
		
	}
	
	/**
	 * 閾ｪ蠕九ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蜈医〒萓句､悶↓縺弱ｊ貎ｰ縺励◆譎ゅ・
	 * 閾ｪ蠕九ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺ｧ萓句､悶↓縺ｪ縺｣縺ｦ繧ょ他縺ｳ蜃ｺ縺怜・縺ｧ繧ｭ繝｣繝・メ縺励※縺・ｌ縺ｰ蝠城｡後↑縺・
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void crushExceptionInAutonomousTransaction(){	
		
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead().find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			//蜻ｼ縺ｳ蜃ｺ縺怜・縺ｧ萓句､匁升繧翫▽縺ｶ縺励※縺・※繧ゅΟ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ縺後◆縺｣縺ｦ縺・ｌ縺ｰ繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｧ繧ｳ繝溘ャ繝郁ｦ∵ｱゅ′螳溯｡後＆繧後※萓句､也匱逕・
			service.crushException();
			fail();
			//縺薙％縺ｧ繧ｭ繝｣繝・メ縺励↑縺・→蜻ｼ縺ｳ縺縺怜・繧ゅΟ繝ｼ繝ｫ繝舌ャ繧ｯ縺ｫ縺ｪ繧・
		}catch(TransactionSystemException pe){
			pe.printStackTrace();
			assertEquals(RollbackException.class,pe.getCause().getClass());
		}
		
	}
	
	/**
	 * 隱ｭ縺ｿ蜿悶ｊ蟆ら畑縺ｮ閾ｪ蠕九ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蜈医〒萓句､悶↓縺弱ｊ貎ｰ縺励◆譎・
	 * 邨仙ｱ繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺碁幕蟋九＆繧後さ繝溘ャ繝医＠縺ｫ縺・￥縺ｮ縺ｧreadOnly=false縺ｨ縺励◆蝣ｴ蜷医→縺翫↑縺倡ｵ先棡縺ｫ縺ｪ繧九・
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void crushExceptionInReadOnlyAutonomousTransaction(){	
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead().find("1");

		RequiresNewReadOnlyService service = ServiceLocator.lookupByInterface(RequiresNewReadOnlyService.class);
		
		try{
			//蜻ｼ縺ｳ蜃ｺ縺怜・縺ｧ萓句､匁升繧翫▽縺ｶ縺励※縺・※繧ゅΟ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ縺後◆縺｣縺ｦ縺・ｌ縺ｰ繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ蠅・阜縺ｧ繧ｳ繝溘ャ繝郁ｦ∵ｱゅ′螳溯｡後＆繧後※萓句､也匱逕・
			service.crushException();
			fail();
			//縺薙％縺ｧ繧ｭ繝｣繝・メ縺励↑縺・→蜻ｼ縺ｳ縺縺怜・繧ゅΟ繝ｼ繝ｫ繝舌ャ繧ｯ縺ｫ縺ｪ繧・
		}catch(TransactionSystemException pe){
			pe.printStackTrace();
			assertEquals(RollbackException.class,pe.getCause().getClass());
		}
	}
	
	
	/**
	 * 謔ｲ隕ｳ繝ｭ繝・け繧ｨ繝ｩ繝ｼ
	 * @throws SQLException 
	 */
	@Test
	public void queryPessimisticLockError(){	
		
		setUpDataForceCommit("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead();
		query.eq(ITestEntity.TEST,"1");
		
		query.getResultList();	//getSingleResult繧・axResult謖・ｮ壹・蝣ｴ蜷・QL讒区枚繧ｨ繝ｩ繝ｼ縲竊・EclipseLink縺ｮ繝舌げ
		
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			service.test();
			fail();
		}catch(PessimisticLockException pe){
			SQLException sqle = (SQLException)pe.getCause().getCause();
			assertEquals("54",String.valueOf(sqle.getErrorCode()));
		}
		
	}
	
	/**
	 * 謔ｲ隕ｳ繝ｭ繝・け繧ｨ繝ｩ繝ｼ
	 * @throws SQLException 
	 */
	@Test
	public void invalidQueryPessimisticLockError(){	
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
	
		setUpDataForceCommit("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticRead();
		query.eq(ITestEntity.TEST,"1");
		query.setHint(QueryHints.HINT, "/* TEST */");
		
		query.getResultList();	//getSingleResult繧・axResult謖・ｮ壹・蝣ｴ蜷・QL讒区枚繧ｨ繝ｩ繝ｼ縲竊・EclipseLink縺ｮ繝舌げ
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
				
		assertEquals("OK",service.test());
			
	}
	
	
	/**
	 * 繝｡繝・そ繝ｼ繧ｸ謖・ｮ・
	 */
	@Test
	public void existsMesasgeByAnyTrue(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.persist(e);
		
		assertFalse( ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertTrue(query.eq(TEST, "200").existsByAny());
		
		assertFalse( ((ServiceContextImpl)ServiceContext.getCurrentInstance()).getCurrentUnitOfWork().isRollbackOnly());
	}
	
	/**
	 * 繝｡繝・そ繝ｼ繧ｸ謖・ｮ・
	 */
	@Test
	public void dateCheck(){

		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		per.persist(entity);
		
		StrictQuery<DateEntity> query = ormQueryFactory.createStrictQuery(DateEntity.class);
		DateEntity result = query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").findAny();
		assertNull(result);
	}
	
	/**
	 * EasyUpdate#execute縺ｮ繝・せ繝・
	 */
	@Test
	public void easyUpdate(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.persist(e2);
		
		int cnt = createUpdate().filter("e.test = :p1 and e.attr = :p2").set("attr","attr2").execute(Arrays.asList(new Object[]{"A",10}), "200","aa");
		assertEquals(1,cnt);
	}
	
	/**
	 * EasyQuery#list縺ｮ繝・せ繝・
	 */
	@Test
	public void easyList(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.persist(e2);
		
		List<TestEntity> ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").list("200","aa");
		assertEquals(2,ls.size());
	}
	
	/**
	 * EasyQuery#single縺ｮ繝・せ繝・
	 */
	@Test
	public void easySingle(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.persist(e2);
		
		TestEntity ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").single("200","aa");
		assertTrue(ls != null);
	}
	
	/**
	 * 繧ｫ繧ｹ繧ｱ繝ｼ繝・
	 */
	@Test
	public void cascade(){
		ParentEntity p = new ParentEntity();
		p.setTest("1").setAttr("aa").setAttr2(100);
		ChildEntity c1 = new ChildEntity();
		c1.setPk("1").setAttr("bb").setAttr2(2000).setParent(p);
		ChildEntity c2 = new ChildEntity();
		c2.setPk("2").setAttr("cc").setAttr2(10000).setParent(p);
		List<ChildEntity> lc = new ArrayList<ChildEntity>();
		lc.add(c1);
		lc.add(c2);
		p.setChilds(lc);
		per.persist(p);
		
		ParentEntity e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		assertEquals("aa",e.getAttr());
		List<ChildEntity> rs = e.getChilds();
		assertEquals(2,rs.size());
		
		//譖ｴ譁ｰ
		rs.get(0).setAttr("500X");
		rs.get(1).setAttr("500X");
		e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		rs = e.getChilds();
		assertEquals("500X",rs.get(0).getAttr());
		assertEquals("500X",rs.get(1).getAttr());
		
		//detach縺励※蜀肴､懃ｴ｢繝ｻ譖ｴ譁ｰ
		EasyQuery<ParentEntity> q = ormQueryFactory.createEasyQuery(ParentEntity.class)
			.filter("e.test = :p1");
		e = q.single("1");
		per.detach(e);
		e.setAttr("500");
		rs = e.getChilds();
		assertEquals(2,rs.size());
		rs.get(0).setAttr("800X");	
		rs.get(1).setAttr("800X");	
		
		//螟画峩縺後↑縺・％縺ｨ繧堤｢ｺ隱・
		e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		assertEquals("aa",e.getAttr());
		rs = e.getChilds();
		assertEquals("500X",rs.get(0).getAttr());
		assertEquals("500X",rs.get(1).getAttr());
		
	}
	
	/**
	 * @return
	 */
	private EasyQuery<TestEntity> create(){
		return ormQueryFactory.createEasyQuery(TestEntity.class);
	}
	
	/**
	 * @return
	 */
	private EasyUpdate<TestEntity> createUpdate(){
		return ormQueryFactory.createEasyUpdate(TestEntity.class);
	}
	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(StrictQuery<TestEntity> query ){	
		query.between(TEST, "0", "30").eq(TEST,"2").gtEq(TEST, "0").ltEq(TEST, "30").lt(TEST, "30").gt(TEST, "0");		
		query.between(ATTR, "0", "20").eq(ATTR,"2").gtEq(ATTR, "0").ltEq(ATTR, "20").lt(ATTR, "20").gt(ATTR, "0");
		query.between(ATTR2, 0, 100).eq(ATTR2,2).gtEq(ATTR2, 0).ltEq(ATTR2, 100).lt(ATTR2, 100).gt(ATTR2, 0);		
		query.contains(TEST, "2","2","2");
		return query.getResultList();
	}
	
}

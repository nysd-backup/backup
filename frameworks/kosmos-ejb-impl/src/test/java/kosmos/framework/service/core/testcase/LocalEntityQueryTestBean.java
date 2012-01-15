/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.testcase;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;

import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.service.core.ServiceTestContextImpl;
import kosmos.framework.service.core.activation.AbstractServiceLocator;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.entity.ChildEntity;
import kosmos.framework.service.core.entity.DateEntity;
import kosmos.framework.service.core.entity.IDateEntity;
import kosmos.framework.service.core.entity.ITestEntity;
import kosmos.framework.service.core.entity.ParentEntity;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.service.core.services.RequiresNewService;
import kosmos.framework.service.core.transaction.ServiceContext;

import org.eclipse.persistence.config.QueryHints;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class LocalEntityQueryTestBean extends BaseCase {
	
	public void allCondition(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);	
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		TestEntity first = result.get(0);
		per.getEntityManager().detach(first);
		first.setAttr("100");
		
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
		List<TestEntity> updated = getOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
		
		context.setRollbackOnly();
	}

	public void disableDetach(){
		setUpData("TEST.xls");
		//更新前取征E
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		first.setAttr("100");
	
		//更新結果
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity entity = forres.eq(ITestEntity.TEST, "2").getSingleResult();
		assertEquals("100",entity.getAttr());
		
		context.setRollbackOnly();
	}


	/**
	 * 更新後検索
	 */
	public void updateAny(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		//更新
		StrictUpdate<TestEntity> update = ormQueryFactory.createStrictUpdate(TestEntity.class);
		update.eq(ITestEntity.TEST, "2").set(ITestEntity.ATTR, "AAA").update();
		
		//検索
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		
		//更新結果(NamedUpdate更新前に検索してぁE��ば永続化コンチE��スト�E更新前キャチE��ュが使用されるためrefleshする忁E��あり。今回はNamedUpdate実行してぁE��ぁE�Eでreflesh不要E��E
		TestEntity entity = query.eq(ITestEntity.TEST, "2").getSingleResult();
		assertEquals("AAA",entity.getAttr());
		
		context.setRollbackOnly();
	}
	

	/**
	 * 1件取得　降頁E��ーチE
	 */

	public void getSingleResultWithDesc(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(ITestEntity.TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */

	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).asc(ITestEntity.TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
		
		context.setRollbackOnly();
	}
	

	/**
	 * 2件目取征E
	 */

	public void getSingleResultSetFirstWithDesc(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(ITestEntity.TEST);
		query.setFirstResult(1);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 2件目から取征E
	 */

	public void getResultSetFirst(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.setFirstResult(1);
		List<TestEntity> result = query.getResultList();
		assertEquals(1,result.size());
		assertEquals("2",result.get(0).getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 2件目から3件目取征E
	 */

	public void getResultSetFirstMax2(){
		setUpData("TEST.xls");
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.getEntityManager().persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		per.getEntityManager().persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.getEntityManager().persist(t);
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(ITestEntity.TEST);
		query.contains(ITestEntity.TEST, "0","1,","2","900","901","902");
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
		
		//更新
		result.get(0).setAttr("AAA");
		per.getEntityManager().flush();
		
		//楽観ロチE��番号インクリメント確誁E
		result = query.getResultList();		
		assertEquals(2,result.get(0).getVersion());
		
		context.setRollbackOnly();
	}
	
	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void nodataError(){
//		try{	
//			LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).enableNoDataError();
//			query.eq(ITestEntity.TEST, "AGA").getSingleResult();
//			context.setRollbackOnly();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//			context.setRollbackOnly();
//		}catch(Throwable t){
//			t.printStackTrace();
//			fail();
//		}
//	}
	
	/**
	 * PK検索
	 */

	public void find(){	
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		per.getEntityManager().detach(result);
		result.setAttr("1100");
		
		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("3",result.getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * PK検索、更新
	 */

	public void findDisableDetach(){
		setUpData("TEST.xls");	
			LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		result.setAttr("1100");
		
		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("1100",result.getAttr());
		context.setRollbackOnly();
	}

//	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void findNodataError(){
//		try{	LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//
//			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
//			query.enableNoDataError();
//			query.find("AA");
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}
//		context.setRollbackOnly();
//	}
	/**
	
//	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void findAnyNodataError(){
//		try{	LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//
//			StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
//			query.enableNoDataError();
//			query.eq(ITestEntity.TEST, "aaa");
//			query.findAny();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}catch(Throwable t){
//			fail();
//		}
//		context.setRollbackOnly();
//	}
	
	/**
	 *  存在チェチE�� not 
	 */

	public void exists(){
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
	setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		assertTrue(query.exists());
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void isEmptyPK(){
		setUpData("TEST.xls");
			LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertFalse(query.exists("AGA"));
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void existsPK(){
		setUpData("TEST.xls");	
			LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertTrue(query.exists("1"));
		context.setRollbackOnly();
	}
	
	/**
	 * 一意制紁E��ラー
	 */
	
	public void uniqueConstraintError(){
		setUpData("TEST.xls");	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		per.getEntityManager().persist(entity);
		
		try{
			per.getEntityManager().flush();
			fail();
		}catch(PersistenceException de){
			SQLIntegrityConstraintViolationException sqle = (SQLIntegrityConstraintViolationException)de.getCause().getCause();
			assertEquals("1",String.valueOf(sqle.getErrorCode()));
		}
	
	}
	
	/**
	 * 一意制紁E��ラー無要E
	 */
	
	public void ignoreUniqueConstraintError(){
		setUpData("TEST.xls");
		//一意制紁E��効匁E
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		per.getEntityManager().persist(entity);	
		per.getEntityManager().flush();
		
		impl.setValidOptimisticLockError();
		
		TestEntity entity2 = new TestEntity();
		entity2.setTest("530");
		entity2.setAttr("aaa");
		entity2.setAttr2(30);
	
		per.getEntityManager().persist(entity2);	
		context.setRollbackOnly();

	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	
	public void versionNoError(){	LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
	setUpData("TEST.xls");
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		final TestEntity result = query.find("1");
		result.setVersion(2);
		try{
			per.getEntityManager().flush();
			fail();
		}catch(OptimisticLockException e){
			return;
		}
	}
	/**
	 * ロチE��連番チェチE��エラー無要E
	 */
	
	public void ignoreVersionNoError(){
		setUpData("TEST.xls");
		//ロチE��連番エラー無効匁E行単位�E更新をさせる場合、こぁE��るか自律トランザクションにする忁E��がある�E�E
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
			LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		final TestEntity result = query.find("1");
		result.setVersion(2);
		
		
		//ロチE��連番エラー無要EDumyExceptionHandlerで握りつぶし！E
		per.getEntityManager().flush(
//				new FlushHandler(){
//
//			@Override
//			public void handle(RuntimeException pe) {
//				pe.printStackTrace();
//				assertEquals(OptimisticLockException.class,pe.getClass());		
//				//リフレチE��ュしてロチE��連番をDBと合わせておかなぁE��次のFlushでも失敗してしまぁE��E
//				per.reflesh(result);
//			}
//			
//		}
				);
		impl.setValidOptimisticLockError();
		
		//バ�Eジョン番号を指定しなぁE��め更新成功
		TestEntity res2 = query.find("2");
		assertEquals(0,res2.getVersion());
		res2.setAttr("aa");
		per.getEntityManager().flush();

		assertEquals("aa", query.find("2").getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * 悲観ロチE��エラー無効匁E
	 * @throws SQLException 
	 */

	public void invalidFindWithLockNoWaitError(){
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		ormQueryFactory.createStrictQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");
	
		assertEquals("OK",service.test());	
		
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	public void findWithLockNoWaitError(){	
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		ormQueryFactory.createStrictQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

		try{
			//トランザクション墁E��でもスローされた例外�Eそ�EままキャチE��可能
			service.test();
			fail();
		}catch(EJBException ejbe){
			Exception e = ejbe.getCausedByException();
			if(e instanceof PessimisticLockException ){
				PessimisticLockException pe = (PessimisticLockException)e;
				SQLException sqle = (SQLException)pe.getCause().getCause();
				assertEquals("54",String.valueOf(sqle.getErrorCode()));
			}else{
				ejbe.printStackTrace();
				fail();
			}
		}
		
	}
	
	/**
	 * 自律トランザクション先で例外にぎり潰した時、E
	 * 自律トランザクションで例外になっても呼び出し�EでキャチE��してぁE��ば問題なぁE
	 * 
	 * @throws SQLException 
	 */

	public void crushExceptionInAutonomousTransaction(){	
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		ormQueryFactory.createStrictQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

		//Springと異なり、自律トランザクション先のEntityManagerがrollbackOnlyでもExceptionはスローされない。
		assertEquals("NG",service.crushException());
			
		
	}
	
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */

	public void queryPessimisticLockError(){	
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		
		query.getResultList();	//getSingleResultめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		try{
			service.test();			
			fail();
		}catch(EJBException ejbe){
			Exception e = ejbe.getCausedByException();
			if(e instanceof PessimisticLockException ){
				PessimisticLockException pe = (PessimisticLockException)e;
				SQLException sqle = (SQLException)pe.getCause().getCause();
				assertEquals("54",String.valueOf(sqle.getErrorCode()));
			}else{
				ejbe.printStackTrace();
				fail();
			}
		}
	
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */

	public void invalidQueryPessimisticLockError(){	
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		query.setHint(QueryHints.HINT, "/* TEST */");
		
		query.getResultList();			
		assertEquals("OK",service.test());
			
	}
	
	
	/**
	 * メチE��ージ持E��E
	 */
	
	/**
	 * メチE��ージ持E��E
	 */

	public void dateCheck(){
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		per.getEntityManager().persist(entity);
		
		StrictQuery<DateEntity> query = ormQueryFactory.createStrictQuery(DateEntity.class);
		assertFalse(query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").exists());
		context.setRollbackOnly();
	}
	
	/**
	 * EasyUpdate#executeのチE��チE
	 */

	public void easyUpdate(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e2);
		
		int cnt = createUpdate().filter("e.test = :p1 and e.attr = :p2").set("attr","attr2").execute(Arrays.asList(new Object[]{"A",10}), "200","aa");
		assertEquals(1,cnt);
		context.setRollbackOnly();
	}
	
	/**
	 * EasyQuery#listのチE��チE
	 */

	public void easyList(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e2);
		
		List<TestEntity> ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").list("200","aa");
		assertEquals(2,ls.size());
		context.setRollbackOnly();
	}
	
	/**
	 * EasyQuery#singleのチE��チE
	 */

	public void easySingle(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.getEntityManager().persist(e2);
		
		TestEntity ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").single("200","aa");
		assertTrue(ls != null);
		context.setRollbackOnly();
	}
	
	/**
	 * カスケーチE
	 */

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
		per.getEntityManager().persist(p);
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		ParentEntity e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		assertEquals("aa",e.getAttr());
		List<ChildEntity> rs = e.getChilds();
		assertEquals(2,rs.size());
		
		//更新
		rs.get(0).setAttr("500X");
		rs.get(1).setAttr("500X");
		e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		rs = e.getChilds();
		assertEquals("500X",rs.get(0).getAttr());
		assertEquals("500X",rs.get(1).getAttr());
		
		//detachして再検索・更新
		EasyQuery<ParentEntity> q = ormQueryFactory.createEasyQuery(ParentEntity.class)
			.filter("e.test = :p1");
		e = q.single("1");
		per.getEntityManager().detach(e);
		e.setAttr("500");
		rs = e.getChilds();
		assertEquals(2,rs.size());
		rs.get(0).setAttr("800X");	
		rs.get(1).setAttr("800X");	
		
		//変更がなぁE��とを確誁E
		e = ormQueryFactory.createEasyQuery(ParentEntity.class).filter("e.test = :p1").single("1");
		assertNotNull(e);
		assertEquals("aa",e.getAttr());
		rs = e.getChilds();
		assertEquals("500X",rs.get(0).getAttr());
		assertEquals("500X",rs.get(1).getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * @return
	 */
	private EasyQuery<TestEntity> create(){
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		return ormQueryFactory.createEasyQuery(TestEntity.class);
	}
	
	/**
	 * @return
	 */
	private EasyUpdate<TestEntity> createUpdate(){
		LimitedOrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		return ormQueryFactory.createEasyUpdate(TestEntity.class);
	}
	
	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(StrictQuery<TestEntity> query ){	
		query.between(ITestEntity.TEST, "0", "30").eq(ITestEntity.TEST,"2").gtEq(ITestEntity.TEST, "0").ltEq(ITestEntity.TEST, "30").lt(ITestEntity.TEST, "30").gt(ITestEntity.TEST, "0");		
		query.between(ITestEntity.ATTR, "0", "20").eq(ITestEntity.ATTR,"2").gtEq(ITestEntity.ATTR, "0").ltEq(ITestEntity.ATTR, "20").lt(ITestEntity.ATTR, "20").gt(ITestEntity.ATTR, "0");
		query.between(ITestEntity.ATTR2, 0, 100).eq(ITestEntity.ATTR2,2).gtEq(ITestEntity.ATTR2, 0).ltEq(ITestEntity.ATTR2, 100).lt(ITestEntity.ATTR2, 100).gt(ITestEntity.ATTR2, 0);		
		query.contains(ITestEntity.TEST, "2","2","2");
		return query.getResultList();
	}
	
	
	public void remote() {
		
	}
}

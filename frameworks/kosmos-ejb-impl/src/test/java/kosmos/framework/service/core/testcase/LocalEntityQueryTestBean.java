/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.testcase;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;

import kosmos.framework.service.core.ServiceTestContextImpl;
import kosmos.framework.service.core.activation.AbstractServiceLocator;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.entity.DateEntity;
import kosmos.framework.service.core.entity.IDateEntity;
import kosmos.framework.service.core.entity.ITestEntity;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.service.core.services.RequiresNewService;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.orm.OrmQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.orm.OrmUpdate;

import org.eclipse.persistence.config.QueryHints;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class LocalEntityQueryTestBean extends BaseCase {
	
	public void duplicateError(){
		
		//1件目
		TestEntity e = new TestEntity();
		e.setAttr("attr");
		e.setTest("1");
		per.getEntityManager().persist(e);
		
		//2件目
		TestEntity e2 = new TestEntity();
		e2.setAttr("attr");
		e2.setTest("1");
		per.getEntityManager().persist(e2);
		
		try{
			per.getEntityManager().flush();
			fail();
		}catch(PersistenceException dbe){
			dbe.printStackTrace();
		}
	}
	
	public void allCondition(){
		setUpData("TEST.xls");
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);	
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		TestEntity first = result.get(0);
		per.getEntityManager().detach(first);
		first.setAttr("100");
		
		OrmQuery<TestEntity> forres = ormQueryFactory.createQuery(TestEntity.class);
		List<TestEntity> updated = getOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
		
		context.setRollbackOnly();
	}

	public void disableDetach(){
		setUpData("TEST.xls");
		//更新前取征E
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		first.setAttr("100");
	
		//更新結果
		OrmQuery<TestEntity> forres = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity entity = forres.eq(ITestEntity.TEST, "2").getSingleResult();
		assertEquals("100",entity.getAttr());
		
		context.setRollbackOnly();
	}


	/**
	 * 更新後検索
	 */
	public void updateAny(){
		setUpData("TEST.xls");
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		//更新
		OrmUpdate<TestEntity> update = ormQueryFactory.createUpdate(TestEntity.class);
		update.eq(ITestEntity.TEST, "2").set(ITestEntity.ATTR, "AAA").update();
		
		//検索
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);		
		
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(ITestEntity.TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */

	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).asc(ITestEntity.TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
		
		context.setRollbackOnly();
	}
	

	/**
	 * 2件目取征E
	 */

	public void getSingleResultSetFirstWithDesc(){
		setUpData("TEST.xls");
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(ITestEntity.TEST);
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.getEntityManager().persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		per.getEntityManager().persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.getEntityManager().persist(t);
		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(ITestEntity.TEST);
		query.contains(ITestEntity.TEST, Arrays.asList("0","1,","2","900","901","902"));
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
//			OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).enableNoDataError();
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity result = query.find("1");
		per.getEntityManager().detach(result);
		result.setAttr("1100");
		
		OrmQuery<TestEntity> query2 = ormQueryFactory.createQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("3",result.getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * PK検索、更新
	 */

	public void findDisableDetach(){
		setUpData("TEST.xls");	
			OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity result = query.find("1");
		result.setAttr("1100");
		
		OrmQuery<TestEntity> query2 = ormQueryFactory.createQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("1100",result.getAttr());
		context.setRollbackOnly();
	}

//	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void findNodataError(){
//		try{	OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
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
//		try{	OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
//
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);		
		assertTrue(query.getSingleResult() == null);
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void isEmptyPK(){
		setUpData("TEST.xls");
			OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		assertFalse(query.find("AGA") != null);
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void existsPK(){
		setUpData("TEST.xls");	
			OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		assertTrue(query.find("1") != null);
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
	
	public void versionNoError(){	OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
	setUpData("TEST.xls");
		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
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
		
			OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
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
		
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");
	
		assertEquals("OK",service.test());	
		
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	public void findWithLockNoWaitError(){	
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.persist();
		
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

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
		
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

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
		
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
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
		
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
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
		OrmQueryFactory ormQueryFactory = AbstractServiceLocator.createDefaultOrmQueryFactory();

		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		per.getEntityManager().persist(entity);
		
		OrmQuery<DateEntity> query = ormQueryFactory.createQuery(DateEntity.class);
		assertFalse(query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").getSingleResult()!=null);
		context.setRollbackOnly();
	}

	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(OrmQuery<TestEntity> query ){	
		query.between(ITestEntity.TEST, "0", "30").eq(ITestEntity.TEST,"2").gtEq(ITestEntity.TEST, "0").ltEq(ITestEntity.TEST, "30").lt(ITestEntity.TEST, "30").gt(ITestEntity.TEST, "0");		
		query.between(ITestEntity.ATTR, "0", "20").eq(ITestEntity.ATTR,"2").gtEq(ITestEntity.ATTR, "0").ltEq(ITestEntity.ATTR, "20").lt(ITestEntity.ATTR, "20").gt(ITestEntity.ATTR, "0");
		query.between(ITestEntity.ATTR2, 0, 100).eq(ITestEntity.ATTR2,2).gtEq(ITestEntity.ATTR2, 0).ltEq(ITestEntity.ATTR2, 100).lt(ITestEntity.ATTR2, 100).gt(ITestEntity.ATTR2, 0);		
		query.contains(ITestEntity.TEST, Arrays.asList("2","2","2"));
		return query.getResultList();
	}
	
	
	public void remote() {
		
	}
}

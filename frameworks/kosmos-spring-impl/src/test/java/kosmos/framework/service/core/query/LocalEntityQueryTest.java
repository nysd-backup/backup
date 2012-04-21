/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.RollbackException;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.test.RequiresNewReadOnlyService;
import kosmos.framework.service.test.RequiresNewService;
import kosmos.framework.service.test.ServiceTestContextImpl;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.DateEntity;
import kosmos.framework.service.test.entity.IDateEntity;
import kosmos.framework.service.test.entity.ITestEntity;
import kosmos.framework.service.test.entity.TestEntity;
import kosmos.framework.sqlclient.orm.OrmQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.orm.OrmUpdate;

import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionSystemException;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */

@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalEntityQueryTest extends ServiceUnit implements ITestEntity{

	@Resource
	private OrmQueryFactory ormQueryFactory;

	@Autowired
	private EntityManagerProvider per;

	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void allCondition() throws SQLException{	
		
		setUpData("TEST.xls");
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
	}
	
	/**
	 * チE��タチE��
	 */
	@Test
	public void disableDetach(){
		setUpData("TEST.xls");
		//更新前取征E
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		first.setAttr("100");
	
		//更新結果
		OrmQuery<TestEntity> forres = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity entity = forres.eq(TEST, "2").getSingleResult();
		assertEquals("100",entity.getAttr());
	}
	
	/**
	 * 更新後検索
	 */
	@Test
	public void updateAny(){
		
		setUpData("TEST.xls");
		
		//更新
		OrmUpdate<TestEntity> update = ormQueryFactory.createUpdate(TestEntity.class);
		update.eq(TEST, "2").set(ATTR, "AAA").update();
		
		//検索
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);		
		
		//更新結果(NamedUpdate更新前に検索してぁE��ば永続化コンチE��スト�E更新前キャチE��ュが使用されるためrefleshする忁E��あり。今回はNamedUpdate実行してぁE��ぁE�Eでreflesh不要E��E
		TestEntity entity = query.eq(TEST, "2").getSingleResult();
		assertEquals("AAA",entity.getAttr());
	}
	
	/**
	 * 更新後検索
	 */
	@Test
	public void updateBatch(){
		TestEntity e1 = new TestEntity();
		e1.setTest("10").setAttr("2").setAttr2(1);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("20").setAttr("2").setAttr2(1);
		
		TestEntity e3 = new TestEntity();
		e3.setTest("30").setAttr("2").setAttr2(1);
		
		per.getEntityManager().persist(e1);
		per.getEntityManager().persist(e2);
		per.getEntityManager().persist(e3);
		
		per.getEntityManager().flush();
		
		//検索
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);		
		List<TestEntity> result = query.getResultList();
		result.get(0).setAttr("100");
		result.get(1).setAttr("20");
		result.get(2).setAttr("aaaa");
		per.getEntityManager().flush();
	}
	
	/**
	 * 1件取得　降頁E��ーチE
	 */
	@Test
	public void getSingleResultWithDesc(){
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */
	@Test
	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).asc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	

	/**
	 * 2件目取征E
	 */
	@Test
	public void getSingleResultSetFirstWithDesc(){
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(TEST);
		query.setFirstResult(1);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	
	/**
	 * 2件目から取征E
	 */
	@Test
	public void getResultSetFirst(){
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		query.setFirstResult(1);
		List<TestEntity> result = query.getResultList();
		assertEquals(1,result.size());
		assertEquals("2",result.get(0).getAttr());
	}
	
	/**
	 * 2件目から3件目取征E
	 */
	@Test
	public void getResultSetFirstMax2(){
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
		
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).desc(TEST);
		query.contains(TEST, Arrays.asList("0","1,","2","900","901","902"));
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
	}
	
	/**
	 * 0件シスチE��エラー
	 */
//	@Test
//	public void nodataError(){
//		try{
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).enableNoDataError();
//			query.eq(TEST, "AGA").getSingleResult();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}catch(Throwable t){
//			fail();
//		}
//	}
	
	/**
	 * PK検索
	 */
	@Test
	public void find(){	
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity result = query.find("1");
		per.getEntityManager().detach(result);
		result.setAttr("1100");
		
		OrmQuery<TestEntity> query2 = ormQueryFactory.createQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("3",result.getAttr());
		
	}
	
	/**
	 * PK検索、更新
	 */
	@Test
	public void findDisableDetach(){
	
		setUpData("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		TestEntity result = query.find("1");
		result.setAttr("1100");
		
		OrmQuery<TestEntity> query2 = ormQueryFactory.createQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("1100",result.getAttr());
	}

//	/**
//	 * 0件シスチE��エラー
//	 */
//	@Test
//	public void findNodataError(){
//		try{
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
//			query.enableNoDataError();
//			query.find("AA");
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}
//	}

	
//	/**
//	 * 0件シスチE��エラー
//	 */
//	@Test
//	public void findAnyNodataError(){
//		try{
//			OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
//			query.enableNoDataError();
//			query.eq(TEST, "aaa");
//			query.findAny();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}catch(Throwable t){
//			fail();
//		}
//	}
	
	/**
	 * 一意制紁E��ラー
	 */
	@Test	
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
			SQLException sqle = (SQLException)de.getCause().getCause();
			assertEquals("1",String.valueOf(sqle.getErrorCode()));
		}
	
	}
	
	/**
	 * 一意制紁E��ラー無要E
	 */
	@Test	
	public void ignoreUniqueConstraintError(){
		
		//一意制紁E��効匁E
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpData("TEST.xls");
	
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

	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	@Test	
	public void versionNoError(){
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
	@Test	
	public void ignoreVersionNoError(){
		
		//ロチE��連番エラー無効匁E行単位�E更新をさせる場合、こぁE��るか自律トランザクションにする忁E��がある�E�E
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpData("TEST.xls");
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
	}
	
	/**
	 * 悲観ロチE��エラー無効匁E
	 * @throws SQLException 
	 */
	@Test
	public void invalidFindWithLockNoWaitError(){	
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
		
		setUpDataForceCommit("TEST.xls");
		
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		assertEquals("OK",service.test());	
		
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void findWithLockNoWaitError(){	
		
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			//トランザクション墁E��でもスローされた例外�Eそ�EままキャチE��可能
			service.test();
			fail();
		}catch(PessimisticLockException pe){
			SQLException sqle = (SQLException)pe.getCause().getCause();
			assertEquals("54",String.valueOf(sqle.getErrorCode()));
		}
		
	}
	
	/**
	 * 自律トランザクション先で例外にぎり潰した時、E
	 * 自律トランザクションで例外になっても呼び出し�EでキャチE��してぁE��ば問題なぁE
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void crushExceptionInAutonomousTransaction(){	
		
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
		try{
			//呼び出し�Eで例外握りつぶしてぁE��もロールバックフラグがたってぁE��ばトランザクション墁E��でコミット要求が実行されて例外発甁E
			service.crushException();
			fail();
			//ここでキャチE��しなぁE��呼びだし�EもロールバックになめE
		}catch(TransactionSystemException pe){
			pe.printStackTrace();
			assertEquals(RollbackException.class,pe.getCause().getClass());
		}
		
	}
	
	/**
	 * トランザクション境界でaddMessage
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void catchTransactionSystemException(){	
		
		//Transactionをrollback状態としても例外がすろーされなければ呼び出し元でも例外にならない。
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		service.addMessage();
		
	}
	
	/**
	 * 読み取り専用の自律トランザクション先で例外にぎり潰した晁E
	 * 結局トランザクションが開始されコミットしにぁE��のでreadOnly=falseとした場合とおなじ結果になる、E
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void crushExceptionInReadOnlyAutonomousTransaction(){	
		setUpDataForceCommit("TEST.xls");
		ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find("1");

		RequiresNewReadOnlyService service = ServiceLocator.lookupByInterface(RequiresNewReadOnlyService.class);
		
		try{
			//呼び出し�Eで例外握りつぶしてぁE��もロールバックフラグがたってぁE��ばトランザクション墁E��でコミット要求が実行されて例外発甁E
			service.crushException();
			fail();
			//ここでキャチE��しなぁE��呼びだし�EもロールバックになめE
		}catch(TransactionSystemException pe){
			pe.printStackTrace();
			assertEquals(RollbackException.class,pe.getCause().getClass());
		}
	}
	
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void queryPessimisticLockError(){	
		
		setUpDataForceCommit("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		
		query.getResultList();	//getSingleResultめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		
		
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
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void invalidQueryPessimisticLockError(){	
		
		ServiceTestContextImpl impl = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();
		impl.setSuppressOptimisticLockError();
	
		setUpDataForceCommit("TEST.xls");
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		query.setHint(QueryHints.HINT, "/* TEST */");
		
		query.getResultList();	//getSingleResultめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
				
		assertEquals("OK",service.test());
			
	}
	
	/**
	 * メチE��ージ持E��E
	 */
	@Test
	public void dateCheck(){

		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		per.getEntityManager().persist(entity);
		
		OrmQuery<DateEntity> query = ormQueryFactory.createQuery(DateEntity.class);
		DateEntity e = query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").getSingleResult();
		assertNull(e);
	}
	
	
	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(OrmQuery<TestEntity> query ){	
		query.between(TEST, "0", "30").eq(TEST,"2").gtEq(TEST, "0").ltEq(TEST, "30").lt(TEST, "30").gt(TEST, "0");		
		query.between(ATTR, "0", "20").eq(ATTR,"2").gtEq(ATTR, "0").ltEq(ATTR, "20").lt(ATTR, "20").gt(ATTR, "0");
		query.between(ATTR2, 0, 100).eq(ATTR2,2).gtEq(ATTR2, 0).ltEq(ATTR2, 100).lt(ATTR2, 100).gt(ATTR2, 0);		
		query.contains(TEST, Arrays.asList("2","2","2"));
		return query.getResultList();
	}
	
}

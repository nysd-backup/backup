/**
 * Copyright 2011 the original author
 */
package service.core.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PessimisticLockException;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.config.QueryHints;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import alpha.framework.domain.activation.ServiceLocator;
import alpha.sqlclient.exception.UniqueConstraintException;
import alpha.sqlclient.orm.ComparingOperand;
import alpha.sqlclient.orm.CriteriaModifyQuery;
import alpha.sqlclient.orm.CriteriaQueryFactory;
import alpha.sqlclient.orm.CriteriaReadQuery;
import alpha.sqlclient.orm.EntityManagerImpl;
import alpha.sqlclient.orm.FixString;

import service.test.RequiresNewNativeReadOnlyService;
import service.test.RequiresNewNativeService;
import service.test.ServiceUnit;
import service.test.entity.DateEntity;
import service.test.entity.FastEntity;
import service.test.entity.IDateEntity;
import service.test.entity.IFastEntity;
import service.test.entity.ITestEntity;
import service.test.entity.TestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */

@ContextConfiguration(locations = "/META-INF/context/oracleAgentNativeApplicationContext.xml")
public class LocalPureNativeEntityQueryTest extends ServiceUnit implements ITestEntity{

	@Resource
	private CriteriaQueryFactory ormQueryFactory;

	@Resource(name="txEntityManager")
	private EntityManagerImpl per;
	
	@Override
	protected EntityManager getEntityManager() {
		return per;
	}
	
	
	/**
	 * @see alpha.domain.framework.test.ServiceUnit#setUpData(java.lang.String)
	 */
	@Override
	protected void setUpData(String dataPath){
		
		try{
			Connection con = per.unwrap(Connection.class);
			String userName = con.getMetaData().getUserName();
			connection = new DatabaseConnection(con,userName);
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see alpha.domain.framework.test.ServiceUnit#setUpDataForceCommit(java.lang.String)
	 */
	@Override
	protected void setUpDataForceCommit(String dataPath){
		
		try{
			DataSource ds = ServiceLocator.getService("dataSource");
			Connection con = ds.getConnection();
			String userName = con.getMetaData().getUserName();
			connection = new DatabaseConnection(con,userName);
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			con.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void allCondition() throws SQLException{	
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);	
		
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		CriteriaReadQuery<TestEntity> forres = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
		List<TestEntity> updated = getOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void allConditionFast() throws SQLException, Exception{	
		FastEntity e = new FastEntity();
		e.setTest("1").setAttr("2").setAttr2(2).setVersion(0);
		per.persist(e);
		per.persist(e.clone().setTest("2"));
		
		CriteriaReadQuery<FastEntity> query = ormQueryFactory.createReadQuery(FastEntity.class,per);	
		
		query.setHint(QueryHints.HINT,"/*+ HINT */");		
		query.between(IFastEntity.TEST, "0", "30").eq(IFastEntity.TEST,"2").gtEq(IFastEntity.TEST, "0").ltEq(IFastEntity.TEST, "30").lt(IFastEntity.TEST, "30").gt(IFastEntity.TEST, "0");		
		query.between(IFastEntity.ATTR, "0", "20").eq(IFastEntity.ATTR,"2").gtEq(IFastEntity.ATTR, "0").ltEq(IFastEntity.ATTR, "20").lt(IFastEntity.ATTR, "20").gt(IFastEntity.ATTR, "0");
		query.between(IFastEntity.ATTR2, 0, 100).eq(IFastEntity.ATTR2,2).gtEq(IFastEntity.ATTR2, 0).ltEq(IFastEntity.ATTR2, 100).lt(IFastEntity.ATTR2, 100).gt(IFastEntity.ATTR2, 0);		
		query.contains(IFastEntity.TEST, Arrays.asList("2","2","2"));
		List<FastEntity> result = query.getResultList();
		assertEquals(1,result.size());
		
		FastEntity updatable = result.get(0).clone().setAttr("1111");
		per.merge(updatable, result.get(0));
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void updateFast() throws SQLException, Exception{	
		FastEntity e = new FastEntity();
		e.setTest("1").setAttr("2").setAttr2(2).setVersion(0);
		
		List<FastEntity> result = new ArrayList<FastEntity>();
		result.add(e);
		result.add(e.clone().setTest("2"));
		per.batchPersist(result);
		
		FastEntity updatable = e.clone().setTest("2").setAttr("aaaa");
		per.merge(updatable,e.clone().setTest("2"));
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void updateFastFlushable() throws SQLException, Exception{	
		
		FastEntity e = new FastEntity();
		e.setTest("1").setAttr("2").setAttr2(2).setVersion(0);
		List<FastEntity> result = new ArrayList<FastEntity>();
		result.add(e);
		result.add(e.clone().setTest("2"));
		per.batchPersist(result);
		
		FastEntity updatable = e.clone().setTest("2").setAttr("aaaa");
		List<FastEntity> megeable = new ArrayList<FastEntity>();
		megeable.add(updatable);
		per.batchMerge(megeable);

	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void updateFastNull() throws SQLException, Exception{	
		FastEntity e = new FastEntity();
		e.setTest("1").setAttr2(10).setVersion(0).setAttr("a");
		per.persist(e);
		per.persist(e.clone().setTest("2").setAttr("2"));
	
		FastEntity updatable = e.clone().setTest("2").setAttr(null);
		per.merge(updatable,e.clone().setTest("2"));
		
		FastEntity ef = per.find(FastEntity.class,"1");
		assertNotNull(ef.getAttr());
		ef = per.find(FastEntity.class,"2");
		assertNull(ef.getAttr());
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void allConditionFixValue() throws SQLException{	
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);	
		
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getFixOneRecord(query);
	
		assertEquals(1,result.size());
		
		CriteriaReadQuery<TestEntity> forres = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
		List<TestEntity> updated = getFixOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
	}
	
	/**
	 * チE��タチE��
	 */
	@Test
	public void disableDetach(){
		setUpData("TEST.xls");
		//更新前取征E
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		per.merge( first.clone().setAttr("100"),first);
	
		//更新結果
		CriteriaReadQuery<TestEntity> forres = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
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
		CriteriaModifyQuery<TestEntity> update = ormQueryFactory.createModifyQuery(TestEntity.class,per);	
		update.eq(TEST, "2").set(ATTR, "AAA").update();
		
		//検索
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);		
		
		//更新結果(NamedUpdate更新前に検索してぁE��ば永続化コンチE��スト�E更新前キャチE��ュが使用されるためrefleshする忁E��あり。今回はNamedUpdate実行してぁE��ぁE�Eでreflesh不要E��E
		TestEntity entity = query.eq(TEST, "2").getSingleResult();
		assertEquals("AAA",entity.getAttr());
	}
	
	
	

	/**
	 * 更新後検索
	 */
	@Test
	public void updateAnyFix(){
		
		setUpData("TEST.xls");
		
		//更新
		CriteriaModifyQuery<TestEntity> update = ormQueryFactory.createModifyQuery(TestEntity.class,per);
		update.addCriteria(TEST.name(), ComparingOperand.Equal, new FixString("'2'"))
		.set(ATTR.name(), new FixString("'AAA'")).update();
		
		//検索
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);		
		
		
		//更新結果(NamedUpdate更新前に検索してぁE��ば永続化コンチE��スト�E更新前キャチE��ュが使用されるためrefleshする忁E��あり。今回はNamedUpdate実行してぁE��ぁE�Eでreflesh不要E��E
		TestEntity entity = query.eq(TEST, "2").getSingleResult();
		assertEquals("AAA",entity.getAttr());
	}
	
	/**
	 * 1件取得　降頁E��ーチE
	 */
	@Test
	public void getSingleResultWithDesc(){
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per).desc(TEST);
		
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */
	@Test
	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per).asc(TEST);
		
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	

	/**
	 * 2件目取征E
	 */
	@Test
	public void getSingleResultSetFirstWithDesc(){
		setUpData("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per).desc(TEST);
		
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
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per);
		
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
		per.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(1);
		per.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.persist(t);
		
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per).desc(TEST);
		
		query.contains(TEST, Arrays.asList("0","1,","2","900","901","902"));
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	
		assertEquals("900",result.get(1).getAttr());
		
		//更新
		TestEntity findedEntity = result.get(0);
		per.merge(findedEntity.clone().setAttr("AAA"), result.get(0));
		
		//楽観ロチE��番号インクリメント確誁E
		result = query.getResultList();		
		assertEquals(2,result.get(0).getVersion());
	}
	
	/**
	 * PK検索
	 */
	@Test
	public void find(){	
		setUpData("TEST.xls");

		TestEntity result = per.find(TestEntity.class,"1");
		result = per.find(TestEntity.class,"1");
		assertEquals("3",result.getAttr());
		
	}
	
	/**
	 * PK検索、更新
	 */
	@Test
	public void findDisableDetach(){
	
		setUpData("TEST.xls");
		TestEntity result = per.find(TestEntity.class,"1");
		TestEntity updatable = result.clone();
		per.merge(updatable.setAttr("1100"), result);
		
		result = per.find(TestEntity.class,"1");
		assertEquals("1100",result.getAttr());
	}
	
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
		
		try{
			per.persist(entity);
			fail();
		}catch(UniqueConstraintException sqle){
			SQLIntegrityConstraintViolationException bi = (SQLIntegrityConstraintViolationException)sqle.getCause();
			assertEquals("1",String.valueOf(bi.getErrorCode()));
		}
	
	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	@Test	
	public void versionNoError(){
		setUpData("TEST.xls");
		TestEntity result = per.find(TestEntity.class,"1");
		TestEntity updatable = result.clone();
		updatable.setVersion(2);
		try{
			per.merge(updatable, result);
			fail();
		}catch(OptimisticLockException e){
			return;
		}
	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	@Test	
	public void versionNoError2(){
		setUpData("TEST.xls");
		TestEntity updatable = new TestEntity();
		updatable.setVersion(2).setTest("1");
		try{
			per.merge(updatable,updatable.clone().setVersion(1));
			fail();
		}catch(OptimisticLockException e){
			return;
		}
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void findWithLockNoWaitError(){	
		
		setUpDataForceCommit("TEST.xls");
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		per.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		RequiresNewNativeService service = ServiceLocator.getService(RequiresNewNativeService.class);
		
		try{
			//トランザクション墁E��でもスローされた例外�Eそ�EままキャチE��可能
			service.test();
			fail();
		}catch(PessimisticLockException pe){
			SQLException sqle = (SQLException)pe.getCause();
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
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		per.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		
		RequiresNewNativeService service = ServiceLocator.getService(RequiresNewNativeService.class);
		
		assertEquals("NG",service.crushException());
		
	}
	
	/**
	 * トランザクション境界でaddMessage
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void catchTransactionSystemException(){	
		
		//Transactionをrollback状態としても例外がすろーされなければ呼び出し元でも例外にならない。
		RequiresNewNativeService service = ServiceLocator.getService(RequiresNewNativeService.class);
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
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		per.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);

		RequiresNewNativeReadOnlyService service = ServiceLocator.getService(RequiresNewNativeReadOnlyService.class);
		
		assertEquals("NG",service.crushException());
	
	}
	
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void queryPessimisticLockError(){	
		
		setUpDataForceCommit("TEST.xls");
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,per).setLockMode(LockModeType.PESSIMISTIC_READ);
		
		query.eq(ITestEntity.TEST,"1");
		
		query.getResultList();	//getSingleResultめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		
		
		RequiresNewNativeService service = ServiceLocator.getService(RequiresNewNativeService.class);
		
		try{
			service.test();
			fail();
		}catch(PessimisticLockException pe){
			SQLException sqle = (SQLException)pe.getCause();
			assertEquals("54",String.valueOf(sqle.getErrorCode()));
		}
		
	}
	
	/**
	 * メチE��ージ持E��E
	 */
	@Test
	public void dateCheck(){

		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		per.persist(entity);
		
		CriteriaReadQuery<DateEntity> query = ormQueryFactory.createReadQuery(DateEntity.class,per);
		
		assertNull(query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").getSingleResult());
	}
	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(CriteriaReadQuery<TestEntity> query ){	
		query.between(TEST, "0", "30").eq(TEST,"2").gtEq(TEST, "0").ltEq(TEST, "30").lt(TEST, "30").gt(TEST, "0");		
		query.between(ATTR, "0", "20").eq(ATTR,"2").gtEq(ATTR, "0").ltEq(ATTR, "20").lt(ATTR, "20").gt(ATTR, "0");
		query.between(ATTR2, 0, 100).eq(ATTR2,2).gtEq(ATTR2, 0).ltEq(ATTR2, 100).lt(ATTR2, 100).gt(ATTR2, 0);		
		query.contains(TEST, Arrays.asList("2","2","2"));
		return query.getResultList();
	}
	
	/**
	 * @return
	 */
	private List<TestEntity> getFixOneRecord(CriteriaReadQuery<TestEntity> query ){	
		query.between(TEST, "0", "30").addCriteria(TEST.name(),ComparingOperand.Equal,new FixString("2")).addCriteria(TEST.name(), ComparingOperand.GreaterEqual,new FixString("0")).addCriteria(TEST.name(), ComparingOperand.LessEqual, new FixString("30")).addCriteria(TEST.name(), ComparingOperand.LessThan, new FixString("30")).addCriteria(TEST.name(), ComparingOperand.GreaterThan, new FixString("0"));		
		query.between(ATTR, "0", "20").addCriteria(ATTR.name(),ComparingOperand.Equal,new FixString("2")).addCriteria(ATTR.name(),ComparingOperand.GreaterEqual,  new FixString("0")).addCriteria(ATTR.name(), ComparingOperand.LessEqual, new FixString("20")).addCriteria(ATTR.name(), ComparingOperand.LessThan,   new FixString("20")).addCriteria(ATTR.name(), ComparingOperand.GreaterThan,  new FixString("0"));
		query.between(ATTR2, 0, 100).addCriteria(ATTR2.name(),ComparingOperand.Equal,new FixString("2")).addCriteria(ATTR2.name(), ComparingOperand.GreaterEqual, new FixString("0")).addCriteria(ATTR2.name(), ComparingOperand.LessEqual, new FixString("100")).addCriteria(ATTR2.name(), ComparingOperand.LessThan,   new FixString("100")).addCriteria(ATTR2.name(), ComparingOperand.GreaterThan,  new FixString("0"));		
		query.contains(TEST, Arrays.asList("2","2","2"));
		return query.getResultList();
	}
	
}

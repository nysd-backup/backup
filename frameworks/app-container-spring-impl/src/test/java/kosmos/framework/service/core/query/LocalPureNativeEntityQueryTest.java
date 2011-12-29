/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PessimisticLockException;
import javax.sql.DataSource;

import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.test.RequiresNewReadOnlyService;
import kosmos.framework.service.test.RequiresNewService;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.DateEntity;
import kosmos.framework.service.test.entity.IDateEntity;
import kosmos.framework.service.test.entity.ITestEntity;
import kosmos.framework.service.test.entity.TestEntity;
import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.PersistenceManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
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

@ContextConfiguration(locations = "/META-INF/context/oracleAgentNativeApplicationContext.xml")
public class LocalPureNativeEntityQueryTest extends ServiceUnit implements ITestEntity{

	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;

	@Autowired
	private PersistenceManager per;
	
	@Autowired
	private ConnectionProvider provider;
	
	/**
	 * @see kosmos.framework.service.test.ServiceUnit#setUpData(java.lang.String)
	 */
	@Override
	protected void setUpData(String dataPath){
		
		try{
			Connection con = provider.getConnection();
			String userName = con.getMetaData().getUserName();
			connection = new DatabaseConnection(con,userName);
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see kosmos.framework.service.test.ServiceUnit#setUpDataForceCommit(java.lang.String)
	 */
	@Override
	protected void setUpDataForceCommit(String dataPath){
		
		try{
			DataSource ds = ServiceLocator.lookup("dataSource");
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
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);	
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
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
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		per.update( first.clone().setAttr("100"), first);
	
		//更新結果
		StrictQuery<TestEntity> forres = ormQueryFactory.createStrictQuery(TestEntity.class);
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
		StrictUpdate<TestEntity> update = ormQueryFactory.createStrictUpdate(TestEntity.class);
		update.eq(TEST, "2").set(ATTR, "AAA").update();
		
		//検索
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		
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
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("2",result.getAttr());
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */
	@Test
	public void getSingleResultWithAsc(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).asc(TEST);
		TestEntity result = query.getSingleResult();
		assertEquals("3",result.getAttr());
	}
	

	/**
	 * 2件目取征E
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
	 * 2件目から取征E
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
	 * 2件目から3件目取征E
	 */
	@Test
	public void getResultSetFirstMax2(){
		setUpData("TEST.xls");
		
		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.insert(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(1);
		per.insert(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.insert(t);
		
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).desc(TEST);
		query.contains(TEST, "0","1,","2","900","901","902");
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	
		assertEquals("900",result.get(1).getAttr());
		
		//更新
		TestEntity findedEntity = result.get(0);
		per.update(findedEntity.clone().setAttr("AAA"), findedEntity);
		
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
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");

		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("3",result.getAttr());
		
	}
	
	/**
	 * PK検索、更新
	 */
	@Test
	public void findDisableDetach(){
	
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		TestEntity updatable = result.clone();
		per.update(updatable.setAttr("1100"),result);
		
		StrictQuery<TestEntity> query2 = ormQueryFactory.createStrictQuery(TestEntity.class);
		result = query2.find("1");
		assertEquals("1100",result.getAttr());
	}
	
	/**
	 *  存在チェチE�� not 
	 */
	@Test
	public void exists(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);		
		assertTrue(query.exists());
	}
	
	/**
	 * PK存在チェチE��
	 */
	@Test
	public void isEmptyPK(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertFalse(query.exists("AGA"));
	}
	
	/**
	 * PK存在チェチE��
	 */
	@Test
	public void existsPK(){
		setUpData("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		assertTrue(query.exists("1"));
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
			per.insert(entity);
			fail();
		}catch(EntityExistsException sqle){
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
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		TestEntity result = query.find("1");
		TestEntity updatable = result.clone();
		updatable.setVersion(2);
		try{
			per.update(updatable, result);
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
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticReadNoWait().find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
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
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticReadNoWait().find("1");

		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
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
		ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticReadNoWait().find("1");

		RequiresNewReadOnlyService service = ServiceLocator.lookupByInterface(RequiresNewReadOnlyService.class);
		
		assertEquals("NG",service.crushException());
	
	}
	
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void queryPessimisticLockError(){	
		
		setUpDataForceCommit("TEST.xls");
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class).setPessimisticReadNoWait();
		query.eq(ITestEntity.TEST,"1");
		
		query.getResultList();	//getSingleResultめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		
		
		RequiresNewService service = ServiceLocator.lookupByInterface(RequiresNewService.class);
		
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
		per.insert(entity);
		
		StrictQuery<DateEntity> query = ormQueryFactory.createStrictQuery(DateEntity.class);
		assertFalse(query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").exists());
	}
	
	/**
	 * EasyUpdate#executeのチE��チE
	 */
	@Test
	public void easyUpdate(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.insert(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.insert(e2);
		
		int cnt = createUpdate().filter("e.test = :p1 and e.attr = :p2").set("attr","attr2").execute(Arrays.asList(new Object[]{"A",10}), "200","aa");
		assertEquals(1,cnt);
	}
	
	/**
	 * EasyQuery#listのチE��チE
	 */
	@Test
	public void easyList(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.insert(e);
		
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.insert(e2);
		
		List<TestEntity> ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").list("200","aa");
		assertEquals(2,ls.size());
	}
	
	/**
	 * EasyQuery#singleのチE��チE
	 */
	@Test
	public void easySingle(){
		
		TestEntity e = new TestEntity();
		e.setTest("200").setAttr("aa").setAttr2(2);
		per.insert(e);
		TestEntity e2 = new TestEntity();
		e2.setTest("201").setAttr("aa").setAttr2(2);
		per.insert(e2);
		
		TestEntity ls = create().filter("e.test = :p1 or e.attr = :p2").order("e.test asc").single("200","aa");
		assertTrue(ls != null);
	}
	
	/**
	 * カスケーチE
	 */
	
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

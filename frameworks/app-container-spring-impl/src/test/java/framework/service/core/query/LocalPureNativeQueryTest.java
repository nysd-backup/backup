/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import framework.api.dto.ClientSessionBean;
import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.core.exception.system.UnexpectedNoDataFoundException;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.core.transaction.ServiceContext;
import framework.service.test.CachableConst;
import framework.service.test.SampleNativeQuery;
import framework.service.test.SampleNativeQueryConst;
import framework.service.test.ServiceUnit;
import framework.service.test.entity.ITestEntity;
import framework.service.test.entity.TestEntity;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.QueryFactory;

/**
 * SQLエンジンのテスト.
 *
 * @author yoshida-n
 * @version	created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class LocalPureNativeQueryTest extends ServiceUnit implements ITestEntity{
	
	@Resource
	private QueryFactory clientQueryFactory;
	
	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;
	
	@Autowired
	private EntityManagerAccessor per;

	
	/**
	 * 通常検索
	 */
	@Test
	public void select(){
		
		ClientSessionBean bean = ServiceContext.getCurrentInstance().getClientSessionBean();
		bean.setUserId("userId");
		
		setUpData("TEST.xls");
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		
		result.get(0).setAttr("500");

		TestEntity results = query.getSingleResult();
		assertEquals("3",results.getAttr());		
	}
	
	/**
	 * 通常検索if分
	 */
	@Test
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr("1000");
		query.setTest("1");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	

	/**
	 * if文検索
	 * 数値比較、not null、文字列比較
	 */
	@Test
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<TestEntity> result = query.getResultList();
		assertEquals(0,result.size());
	}
	
	/**
	 * 結果0件システムエラー
	 */
	@Test
	public void nodataError(){
		setUpData("TEST.xls");
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class).enableNoDataError();
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
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1");
		assertTrue(query.exists());
	}

	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
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
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class).setMaxResults(2);
		List<TestEntity> e = query.getResultList();
		assertEquals(2,e.size());
	}
	
	/**
	 * setFirstResult、2件目～5件目取得
	 */
	@Test
	public void setFirstResult(){
		setUpData("TEST.xls");
		
		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		per.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの指定は無視される
		per.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		per.persist(t);
		per.flush();
		
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);		
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//必ず楽観ロック番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = clientQueryFactory.createQuery(SampleNativeQueryConst.class);
		c.setTest("1");
		List<TestEntity> e = c.getResultList();
		assertEquals(1,e.size());
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	@Rollback(false)
	public void constAttr(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = clientQueryFactory.createQuery(SampleNativeQueryConst.class);
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
		per.flush();
		
		SampleNativeQueryConst c = clientQueryFactory.createQuery(SampleNativeQueryConst.class);
		c.setArc(CachableConst.TARGET_INT);		
		List<TestEntity> e = c.getResultList();
		assertEquals(1,e.size());
	}
	
	/**
	 * ヒット件数等取得
	 */
	@Test
	public void count(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		assertEquals(2,query.count());
	}
	
	/**
	 * ヒット件数等取得
	 */
	@Test	
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		query.setMaxResults(1);
		NativeResult<TestEntity> result = query.getTotalResult();
		assertEquals(2,result.getHitCount());
		assertTrue(result.isLimited());
		assertEquals(1,result.getResultList().size());
	}
	
	/**
	 * ResultSetフェッチ取得
	 */
	@Test
	public void lazySelect(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = clientQueryFactory.createQuery(SampleNativeQuery.class);
		List<TestEntity> e = query.getFetchResult();
		
		Iterator<TestEntity> itr = e.iterator();
		int cnt = 0;
		while(itr.hasNext()){
			TestEntity next = itr.next();
			if(cnt == 0){
				assertEquals("2",next.getTest());
			}else {
				assertEquals("1",next.getTest());
			}
			cnt++;
		}
		assertFalse(e.isEmpty());
		assertEquals(2,e.size());
	}
}

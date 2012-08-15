/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import service.test.entity.TestEntity;
import client.sql.orm.CriteriaQueryFactory;
import client.sql.orm.CriteriaReadQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Transactional
public class DupplicateService {

	@Resource
	private CriteriaQueryFactory ormQueryFactory;

	@PersistenceContext(unitName="oracle")
	private EntityManager em;
	
	@PersistenceContext(unitName="readOnlyOracle")
	private EntityManager rem;
	
	/**
	 * 成功
	 * @return
	 */
	public int[] test(){
		
		int[] res = new int[2];
		
		//書き込み
		TestEntity e = new TestEntity();
		e.setTest("aaa");
		e.setAttr("aaaa");
		e.setAttr2(100);
		em.persist(e);
		em.flush();
			
		//読み込み専用
		CriteriaReadQuery<TestEntity> query = ormQueryFactory.createReadQuery(TestEntity.class,rem);	
		List<TestEntity> result = query.getResultList();
		System.out.println(result.size());
		res[0] = result.size();
		
		//両用で取得
		CriteriaReadQuery<TestEntity> wquery = ormQueryFactory.createReadQuery(TestEntity.class,em);	
		List<TestEntity> wresult = wquery.getResultList();
		System.out.println(wresult.size());
		res[1] = wresult.size();
		
		return res;
	}
	
	/**
	 *readOnlyEntityManagerProviderは トランザクション開始していないのでflush時にエラー
	 */
	public void fail(){
		
		TestEntity e2 = new TestEntity();
		e2.setTest("aaab");
		e2.setAttr("aaaa");
		e2.setAttr2(100);
		rem.persist(e2);
		rem.flush();
	}
	
}
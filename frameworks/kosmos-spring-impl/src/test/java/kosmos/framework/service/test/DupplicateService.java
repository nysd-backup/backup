/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import java.util.List;

import javax.annotation.Resource;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.service.test.entity.TestEntity;
import kosmos.framework.sqlclient.orm.OrmQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Transactional
@Service
public class DupplicateService {

	@Resource
	private OrmQueryFactory ormQueryFactory;
	
	@Resource
	private OrmQueryFactory readOnlyOrmQueryFactory;

	@Resource
	private EntityManagerProvider entityManagerProvider;
	
	@Resource
	private EntityManagerProvider readOnlyEntityManagerProvider;
	
	
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
		entityManagerProvider.getEntityManager().persist(e);
		entityManagerProvider.getEntityManager().flush();
			
		//読み込み専用
		OrmQuery<TestEntity> query = readOnlyOrmQueryFactory.createQuery(TestEntity.class);	
		List<TestEntity> result = query.getResultList();
		System.out.println(result.size());
		res[0] = result.size();
		
		//両用で取得
		OrmQuery<TestEntity> wquery =ormQueryFactory.createQuery(TestEntity.class);	
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
		readOnlyEntityManagerProvider.getEntityManager().persist(e2);
		readOnlyEntityManagerProvider.getEntityManager().flush();
	}
	
}

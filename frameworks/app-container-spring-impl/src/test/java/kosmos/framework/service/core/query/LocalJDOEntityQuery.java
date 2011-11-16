/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.util.List;

import javax.annotation.Resource;

import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.service.test.ServiceUnit;
import kosmos.framework.service.test.entity.ITestEntity;
import kosmos.framework.service.test.entity.TestEntity;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations={"/META-INF/context/jdoApplicationContext.xml"})
public class LocalJDOEntityQuery extends ServiceUnit implements ITestEntity{

	@Resource
	private AdvancedOrmQueryFactory jdoOrmQueryFactory;
	
	/**
	 * 
	 */
	@Test
	public void getResultList(){
		StrictQuery<TestEntity> query = jdoOrmQueryFactory.createStrictQuery(TestEntity.class);
		query.eq(TEST, "200");
		List<TestEntity> e = query.getResultList();
		e.isEmpty();
	}
}

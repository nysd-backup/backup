/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.service.test.ServiceUnit;
import framework.service.test.entity.ITestEntity;
import framework.service.test.entity.TestEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
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

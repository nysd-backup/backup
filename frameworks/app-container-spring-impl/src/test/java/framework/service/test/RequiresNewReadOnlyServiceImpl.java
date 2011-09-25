/**
 * Use is subject to license terms.
 */
package framework.service.test;

import javax.annotation.Resource;
import javax.persistence.PessimisticLockException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.service.test.entity.TestEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
public class RequiresNewReadOnlyServiceImpl implements RequiresNewReadOnlyService{

	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;
	
	public String test() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.setPessimisticRead();
		query.find("1");
		return "OK";
	}

	@Override
	public String crushException() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰していなければJPASessionのロールバックフラグはtrueになる
			query.setPessimisticRead();
			query.find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

}

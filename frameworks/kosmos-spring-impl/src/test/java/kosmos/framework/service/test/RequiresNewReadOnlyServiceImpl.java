/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.service.test.entity.TestEntity;

import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
public class RequiresNewReadOnlyServiceImpl implements RequiresNewReadOnlyService{

	@Resource
	private LimitedOrmQueryFactory ormQueryFactory;
	
	public String test() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
		query.find("1");
		return "OK";
	}

	@Override
	public String crushException() {
		StrictQuery<TestEntity> query = ormQueryFactory.createStrictQuery(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
			query.find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

}

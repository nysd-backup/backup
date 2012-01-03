/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
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
@Transactional(propagation=Propagation.REQUIRED)
public class MockServiceImpl implements MockService{
	
	//@Resource
	//private OrmQueryFactory ormQueryFactory;
	
	@Autowired
	private MockRequiresNewService ms;

	@Autowired
	private MockService2 m2;
	
	@Override
	@Rollback(false)
	public Object exec(Object v) {
		ms.exec(v);
		m2.exec("100");
		
		//OrmQuery<NotTestEntity> eq = ormQueryFactory.createQuery(NotTestEntity.class);		
		return v;
	}

}

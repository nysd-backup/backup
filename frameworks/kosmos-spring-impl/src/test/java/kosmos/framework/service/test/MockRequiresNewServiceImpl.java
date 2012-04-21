/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.test.entity.TestEntity;

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
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class MockRequiresNewServiceImpl implements MockRequiresNewService{

	@Autowired
	private EntityManagerProvider per;
	
	@Autowired
	private MockService2 s2;
	
	@Override
	@Rollback(false)	
	public Object exec(Object v) {
		s2.exec("111");
		TestEntity e = new TestEntity();
		e.setTest("1");
		e.setAttr("aaa");
		e.setAttr2(2);
		per.getEntityManager().persist(e);
		if( v.equals("AA")){
			ServiceContext.getCurrentInstance().setRollbackOnlyToCurrentTransaction();
		}
		return v;
	}

}

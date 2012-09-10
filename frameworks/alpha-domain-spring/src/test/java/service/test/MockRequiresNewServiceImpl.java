/**
 * Copyright 2011 the original author
 */
package service.test;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import alpha.framework.domain.transaction.ServiceContext;

import service.test.entity.TestEntity;


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

	@PersistenceContext(unitName="oracle")
	private EntityManager em;
	
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
		em.persist(e);
		if( v.equals("AA")){
			ServiceContext.getCurrentInstance().setRollbackOnly();
		}
		return v;
	}

}

package service.test;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import service.test.entity.TestEntity;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRED)
public class MockService2Impl implements MockService2{

	@PersistenceContext(unitName="oracle")
	private EntityManager em;
	
	
	@Override
	@Rollback(false)
	public void exec(String v) {
		TestEntity e = new TestEntity();
		e.setTest("10");
		e.setAttr("aaa");
		e.setAttr2(2);
		em.persist(e);
	}

}

package kosmos.framework.service.test;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.test.entity.TestEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRED)
public class MockService2Impl implements MockService2{

	@Autowired
	private EntityManagerProvider per;
	
	@Override
	@Rollback(false)
	public void exec(String v) {
		TestEntity e = new TestEntity();
		e.setTest("10");
		e.setAttr("aaa");
		e.setAttr2(2);
		per.getEntityManager().persist(e);
	}

}

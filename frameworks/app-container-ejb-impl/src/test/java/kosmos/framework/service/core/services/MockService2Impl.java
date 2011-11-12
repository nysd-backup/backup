package kosmos.framework.service.core.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.core.entity.TestEntity;


@Stateless
public class MockService2Impl implements MockService2{

	@EJB
	private EntityManagerProvider per;
	
	@Override
	public void exec(String v) {
		TestEntity e = new TestEntity();
		e.setTest("10");
		e.setAttr("aaa");
		e.setAttr2(2);
		per.getEntityManager().persist(e);
	}

}

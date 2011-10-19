/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import framework.jpqlclient.api.EntityManagerProvider;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.entity.TestEntity;
import framework.service.ext.transaction.ServiceContextImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class MockRequiresNewServiceImpl implements MockRequiresNewService{

	@EJB
	private EntityManagerProvider per;
	
	@EJB
	private MockService2 s2;
	
	@Override	
	public Object exec(Object v) {
		s2.exec("111");
		TestEntity e = new TestEntity();
		e.setTest("1");
		e.setAttr("aaa");
		e.setAttr2(2);
		per.getEntityManager().persist(e);
		if( v.equals("AA")){
			((ServiceContextImpl)ServiceContext.getCurrentInstance()).setRollbackOnlyToCurrentTransaction();
		}
		return v;
	}

}

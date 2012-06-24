/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import service.entity.TestEntity;
import service.framework.core.transaction.ServiceContext;
import service.framework.core.transaction.ServiceContextImpl;
import service.testcase.BaseCase;




/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class MockRequiresNewServiceImpl extends BaseCase implements MockRequiresNewService{
	
	@EJB
	private MockService2 s2;
	
	@Override	
	public Object exec(Object v) {
		s2.exec("111");
		TestEntity e = new TestEntity();
		e.setTest("1");
		e.setAttr("aaa");
		e.setAttr2(2);
		em.persist(e);
		if( v.equals("AA")){
			((ServiceContextImpl)ServiceContext.getCurrentInstance()).setRollbackOnly();
		}
		return v;
	}

}

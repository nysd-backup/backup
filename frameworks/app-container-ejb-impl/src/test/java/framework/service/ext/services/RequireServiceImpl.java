/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import framework.core.message.ErrorMessage;
import framework.jpqlclient.api.EntityManagerProvider;
import framework.logics.builder.MessageAccessor;
import framework.service.ext.entity.TestEntity;
import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class RequireServiceImpl implements RequireService {


	@EJB
	private EntityManagerProvider ema;
	
	/**
	 * @see framework.service.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		MessageAccessor accessor = ServiceLocatorImpl.getComponentBuilder().createMessageAccessor();
		accessor.addMessage(new ErrorMessage(100));
	}

	/**
	 * @see framework.service.test.RequireService#test()
	 */
	@Override
	public int persist() {
		TestEntity e = new TestEntity();
		e.setTest("105").setAttr("aaa").setAttr2(2222);
		ema.getEntityManager().persist(e);
		ema.getEntityManager().flush();
		return 1;
	}
	
}

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;


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
	 * @see kosmos.framework.service.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		String message = ServiceLocator.createDefaultMessageBuilder().load(new ErrorMessage(100), Locale.getDefault());
		ServiceContext.getCurrentInstance().addError(new ErrorMessage(100),message);
	}

	/**
	 * @see kosmos.framework.service.test.RequireService#test()
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

/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.entity.TestEntity;
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
		MessageBean bean = new MessageBean(new ErrorMessage(100));
		String message = ServiceLocator.createDefaultMessageBuilder().load(bean);
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

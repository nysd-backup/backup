/**
 * Copyright 2011 the original author
 */
package service.services;

import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import client.sql.elink.EntityManagerProvider;

import core.message.MessageBean;
import core.message.MessageResult;

import service.entity.TestEntity;
import service.framework.core.activation.ServiceLocator;
import service.framework.core.transaction.ServiceContext;




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
	 * @see service.framework.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		MessageBean bean = new MessageBean("100");
		MessageResult message = ServiceLocator.createDefaultMessageBuilder().load(bean,Locale.getDefault());
		ServiceContext.getCurrentInstance().addMessage(message);
	}

	/**
	 * @see service.framework.test.RequireService#test()
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

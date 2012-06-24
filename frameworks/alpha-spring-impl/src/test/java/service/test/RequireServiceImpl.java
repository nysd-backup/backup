/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import service.framework.core.transaction.ServiceContext;
import service.test.entity.TestEntity;
import core.message.MessageBean;
import core.message.MessageBuilder;
import core.message.MessageResult;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RequireServiceImpl implements RequireService {	

	@Autowired
	private MessageBuilder builder;
	
	@PersistenceContext(unitName="oracle")
	private EntityManager em;
	
	/**
	 * @see service.framework.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		MessageBean bean = new MessageBean("100");
		MessageResult message = builder.load(bean,Locale.getDefault());
		ServiceContext.getCurrentInstance().addMessage( message);
	}

	/**
	 * @see service.framework.test.RequireService#test()
	 */
	@Override
	public int persist() {
		TestEntity e = new TestEntity();
		e.setTest("105").setAttr("aaa").setAttr2(2222);
		em.persist(e);
		em.flush();
		return 1;
	}
	
}

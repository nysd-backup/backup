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

import alpha.framework.core.message.Message;
import alpha.framework.core.message.MessageArgument;
import alpha.framework.core.message.MessageBuilder;
import alpha.framework.domain.transaction.ServiceContext;

import service.test.entity.TestEntity;


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
	 * @see alpha.domain.framework.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		MessageArgument bean = new MessageArgument("100");
		Message message = builder.load(bean,Locale.getDefault());
		ServiceContext.getCurrentInstance().addMessage( message);
	}

	/**
	 * @see alpha.domain.framework.test.RequireService#test()
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

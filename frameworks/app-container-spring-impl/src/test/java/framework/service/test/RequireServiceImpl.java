/**
 * Copyright 2011 the original author
 */
package framework.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import framework.core.message.ErrorMessage;
import framework.jpqlclient.api.EntityManagerProvider;
import framework.logics.builder.MessageAccessor;
import framework.service.test.entity.TestEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRED)
public class RequireServiceImpl implements RequireService {

	@Autowired
	private MessageAccessor accessor;
	
	@Autowired
	private EntityManagerProvider ema;
	
	/**
	 * @see framework.service.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		accessor.addMessage(new ErrorMessage(1));
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

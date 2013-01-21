/**
 * Copyright 2011 the original author
 */
package service.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import service.test.entity.TestEntity;
import alpha.framework.transaction.TransactionContext;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RequireServiceImpl implements RequireService {	

	@PersistenceContext(unitName="oracle")
	private EntityManager em;
	
	/**
	 * @see alpha.domain.framework.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		TransactionContext.getCurrentInstance().addMessage( "100");
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

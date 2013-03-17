/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.Stateless;

import org.coder.alpha.framework.transaction.TransactionContext;

import service.entity.TestEntity;
import service.testcase.BaseCase;




/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class RequireServiceImpl extends BaseCase implements RequireService {
	
	/**
	 * @see alpha.domain.framework.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		TransactionContext.getCurrentInstance().acceptRollbackTrigger(new RollbackableImpl("100"));
	}

	/**
	 * @see alpha.domain.framework.test.RequireService#test()
	 */
	@Override
	public int persist() {
		TestEntity e = new TestEntity();
		e.setTest("105").setAttr("aaa").setAttr2(2222);
		persist(e);
		flush();
		return 1;
	}
	
}

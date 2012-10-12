/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.Stateless;

import service.entity.TestEntity;
import service.testcase.BaseCase;
import alpha.framework.domain.transaction.DomainContext;




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
		DomainContext.getCurrentInstance().addMessage("100");
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

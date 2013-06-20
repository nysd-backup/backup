/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.Stateless;

import org.coder.alpha.message.context.MessageContext;
import org.coder.alpha.message.target.Message;
import org.coder.alpha.message.target.MessageLevel;

import service.entity.TargetEntity;
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
		Message msg = new Message();
		msg.setMessageLevel(MessageLevel.ERROR.ordinal());
		MessageContext.getCurrentInstance().addMessage(msg);
	}

	/**
	 * @see alpha.domain.framework.test.RequireService#test()
	 */
	@Override
	public int persist() {
		TargetEntity e = new TargetEntity();
		e.setTest("105").setAttr("aaa").setAttr2(2222);
		persist(e);
		flush();
		return 1;
	}
	
}

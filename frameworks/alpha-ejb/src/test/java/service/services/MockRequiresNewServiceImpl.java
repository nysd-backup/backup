/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.coder.alpha.message.context.MessageContext;
import org.coder.alpha.message.object.Message;
import org.coder.alpha.message.object.MessageLevel;

import service.entity.TargetEntity;
import service.testcase.BaseCase;




/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class MockRequiresNewServiceImpl extends BaseCase implements MockRequiresNewService{
	
	@EJB
	private MockService2 s2;
	
	@Override	
	public Object exec(Object v) {
		s2.exec("111");
		TargetEntity e = new TargetEntity();
		e.setTest("1");
		e.setAttr("aaa");
		e.setAttr2(2);
		em.persist(e);
		if( v.equals("AA")){
			Message msg = new Message();
			msg.setMessageLevel(MessageLevel.ERROR.ordinal());
			(MessageContext.getCurrentInstance()).addMessage(msg);
		}
		return v;
	}

}

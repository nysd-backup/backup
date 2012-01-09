/**
 * Copyright 2011 the original author
 */
package kosmos.framework.test.service;

import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.core.message.Messages;
import kosmos.framework.service.core.transaction.ServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Service
@Transactional
public class TestServiceImpl implements TestService{
	
	@Autowired
	private MessageBuilder builder;
	
	@Override
	public void addMessages(int count) {
		
		MessageBean bean = new MessageBean(Messages.MSG_SYS_UNEXPECTED_DATA_FOUND);
		MessageResult message = builder.load(bean);
		ServiceContext.getCurrentInstance().addMessage(message);
		
		MessageBean bean2 = new MessageBean(Messages.MSG_SYS_UNEXPECTED_NO_DATA_FOUND);
		MessageResult message2 = builder.load(bean2);
		ServiceContext.getCurrentInstance().addMessage(message2);
	
	}

	@Override
	public void persist(int count) {
		// TODO Auto-generated method stub
		
	}

}

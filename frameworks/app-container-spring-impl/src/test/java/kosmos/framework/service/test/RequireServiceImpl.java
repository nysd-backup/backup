/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.test.entity.TestEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


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
	
	@Autowired
	private EntityManagerProvider ema;
	
	/**
	 * @see kosmos.framework.service.test.RequireService#test()
	 */
	@Override
	public void addMessage() {
		MessageBean bean = new MessageBean(new ErrorMessage(1));
		String message = builder.load(bean);
		ServiceContext.getCurrentInstance().addError(new ErrorMessage(1), message);
	}

	/**
	 * @see kosmos.framework.service.test.RequireService#test()
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

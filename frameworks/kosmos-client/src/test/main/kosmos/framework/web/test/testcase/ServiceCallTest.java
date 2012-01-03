/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.test.testcase;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import junit.framework.Assert;
import kosmos.framework.core.dto.ReplyMessage;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.exception.SystemException;
import kosmos.framework.web.core.api.service.ServiceCallable;
import kosmos.framework.web.core.api.service.ServiceFacade;
import kosmos.framework.web.core.api.service.ServiceFacadeInjector;
import kosmos.framework.web.test.services.LocalService;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@ServiceCallable
public class ServiceCallTest extends Assert{
	
	@ServiceFacade
	private LocalService service;
	
	public ServiceCallTest(){
		new ServiceFacadeInjector().inject(this,null);
	}
	
	public void normal(){
		service.normal("111");
	}
	
	public void addMessage(){
		try{
			service.addMessage("aaa");
			fail();
		}catch(BusinessException be){
			ReplyMessage[] messages = be.getMessageList();
			assertEquals(1,messages.length);
		}
	}
	
	public void throwError(){
		try{
			service.throwError(null);
			fail();
		}catch(SystemException se){
			
		}
	}
	
	public void databaseError(){
		try{
			service.databaseError("aaa");
			fail();
		}catch(PersistenceException pe){
			SQLException sqle = (SQLException)pe.getCause();
			sqle.getErrorCode();
		}
	}
}

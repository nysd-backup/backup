/**
 * Copyright 2011 the original author
 */
package service.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import service.test.entity.TestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
public class RequiresNewNativeReadOnlyServiceImpl implements RequiresNewNativeReadOnlyService{

	@Autowired
	private EntityManager em;
	
	@PostConstruct
	protected void postConstruct(){
	
	}
	
	public String test() {
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		em.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		return "OK";
	}

	@Override
	public String crushException() {		
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			Map<String,Object> hints = new HashMap<String,Object>();
			hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
			em.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

}

/**
 * Copyright 2011 the original author
 */
package service.test;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
import client.sql.orm.OrmQueryFactory;
import client.sql.orm.OrmSelect;


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

	@Resource
	private OrmQueryFactory ormQueryFactory;
	
	@Autowired
	private EntityManager em;
	
	@PostConstruct
	protected void postConstruct(){
	
	}
	
	public String test() {
		OrmSelect<TestEntity> query = ormQueryFactory.createSelect(TestEntity.class,em);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
		query.find("1");
		return "OK";
	}

	@Override
	public String crushException() {
		OrmSelect<TestEntity> query = ormQueryFactory.createSelect(TestEntity.class,em);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
			query.find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

}

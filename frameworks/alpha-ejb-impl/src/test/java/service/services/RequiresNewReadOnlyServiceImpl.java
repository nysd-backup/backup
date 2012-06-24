/**
 * Copyright 2011 the original author
 */
package service.services;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.config.QueryHints;

import service.entity.TestEntity;
import service.testcase.BaseCase;
import client.sql.orm.OrmSelect;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequiresNewReadOnlyServiceImpl extends BaseCase implements RequiresNewReadOnlyService{

	public String test() {		
		OrmSelect<TestEntity> query = createOrmSelect(TestEntity.class);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
		query.find("1");
		return "OK";
	}

	@Override
	public String crushException() {
		OrmSelect<TestEntity> query = createOrmSelect(TestEntity.class);
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

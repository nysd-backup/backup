package service.services;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import service.entity.TestEntity;
import service.testcase.BaseCase;

@Stateless
public class AsyncSession extends BaseCase{

	@Asynchronous
	public Future<String> execute(EntityManager em) {	
		TestEntity e = new TestEntity();
		e.setTest("aaab");
		e.setAttr("aaa");
		e.setAttr2(100);
		em.persist(e);
		Future<String> value = new AsyncResult<String>("aaa");
		return value;
	}

}
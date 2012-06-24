package service.services;

import javax.ejb.Stateless;

import service.entity.TestEntity;
import service.testcase.BaseCase;




@Stateless
public class MockService2Impl extends BaseCase implements MockService2{
	
	@Override
	public void exec(String v) {
		TestEntity e = new TestEntity();
		e.setTest("10");
		e.setAttr("aaa");
		e.setAttr2(2);
		persist(e);
	}

}

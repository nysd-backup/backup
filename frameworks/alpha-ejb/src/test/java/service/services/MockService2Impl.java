package service.services;

import javax.ejb.Stateless;

import service.entity.TargetEntity;
import service.testcase.BaseCase;




@Stateless
public class MockService2Impl extends BaseCase implements MockService2{
	
	@Override
	public void exec(String v) {
		TargetEntity e = new TargetEntity();
		e.setTest("10");
		e.setAttr("aaa");
		e.setAttr2(2);
		persist(e);
	}

}

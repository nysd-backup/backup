/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class MockServiceImpl implements MockService{
	
	//@Resource
	//private OrmQueryFactory ormQueryFactory;
	
	@EJB
	private MockRequiresNewService ms;

	@EJB
	private MockService2 m2;
	
	@Override
	public Object exec(Object v) {
		ms.exec(v);
		m2.exec("100");
		
		//OrmQuery<NotTestEntity> eq = ormQueryFactory.createQuery(NotTestEntity.class);		
		return v;
	}

}

/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class SessionBeanDelegate {

	@EJB
	private SessionBean bean;
	
//	@EJB
//	private TestBean bean;
	
	public void test(){
		bean.test();
	}
}

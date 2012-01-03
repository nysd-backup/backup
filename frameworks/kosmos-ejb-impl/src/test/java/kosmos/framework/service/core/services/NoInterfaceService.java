/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.ejb.Stateless;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class NoInterfaceService {

	public void test(){
		System.out.println("OK");
	}
}

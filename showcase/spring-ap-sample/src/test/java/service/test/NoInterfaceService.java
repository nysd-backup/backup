/**
 * Copyright 2011 the original author
 */
package service.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Transactional
public class NoInterfaceService {

	public void test(){
		System.out.println("OK");
	}
}

/**
 * Use is subject to license terms.
 */
package framework.service.ext.apis;

import org.junit.Test;

import framework.service.ext.tool.ContainerTestCase;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/03/16 created.
 */
public class CopyOfSampleServiceTest extends ContainerTestCase{
	
	@Test
	public void test()throws Exception{
		try{
			TestService sv = (TestService)(container.getContext().lookup("java:global/service-container/TestServiceImpl"));
			sv.test();
		}catch(Exception e){
			System.out.println("失敗 " + e.getClass());
		}
	}
	
}

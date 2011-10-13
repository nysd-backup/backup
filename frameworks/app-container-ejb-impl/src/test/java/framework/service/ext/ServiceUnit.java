/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import framework.service.core.locator.ServiceLocator;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ServiceUnit {
	
	private static EJBContainer container;
	
	@BeforeClass
    public static void setUpClass() throws Exception {
		Map<String,Object> prop = new HashMap<String,Object>();
		prop.put(EJBContainer.APP_NAME, "test");
		prop.put("org.glassfish.ejb.embedded.glassfish.instance.root", "C:/Env/Personal/glassfishv3/glassfish/domains/domain1");
		container = EJBContainer.createEJBContainer(prop);
		
		StubServiceLocator locator = new StubServiceLocator();
		locator.initialize(new DefaultComponentBuilder());
	}
	@Test
	public void test() throws Exception{
		
		SessionBeanDelegate bean = ServiceLocator.lookup("SessionBeanDelegate");
		bean.test();
		
	}
	
	@AfterClass
    public static void close() throws Exception {
		container.close();
	}
}

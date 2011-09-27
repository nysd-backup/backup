/**
 * Copyright 2011 the original author
 */
package framework.service.ext.tool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * function.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public abstract class ContainerTestCase extends Assert{

	protected static EJBContainer container;
	
	/**
	 * 繧ｳ繝ｳ繝・く繧ｹ繝亥・譛溷喧
	 */
	@BeforeClass
	public static void initialize(){
		
		Map<String,Object> arg0 = new HashMap<String,Object>();
		
		//domain.xml縺九ｉjdbc-resource縺ｪ縺ｩ縺ｮ險ｭ螳壹ｒ隱ｭ縺ｿ霎ｼ繧縺溘ａ縺ｮ險ｭ螳・
		arg0.put("org.glassfish.ejb.embedded.glassfish.instance.root","C:\\Env\\Personal\\glassfishv3\\glassfish\\domains\\domain1");		
		arg0.put("org.glassfish.ejb.embedded.glassfish.installation.root","C:\\Env\\Personal\\glassfishv3\\glassfish");
		
		arg0.put(EJBContainer.APP_NAME, "app");
		
		//test-classes縺ｫclasses縺ｮ繧ｽ繝ｼ繧ｹ繧定ｪｭ縺ｾ縺帙ｋ縺薙→縺ｧtest/resource/ejb-jar縺ｨclasses縺ｮ繧ｯ繝ｩ繧ｹ荳｡譁ｹ縺ｮSessionBean繧剃ｽ懈・蜿ｯ閭ｽ縺ｫ縺吶ｋ
		try{
			FileUtils.copyDirectory(new File("target/classes/framework"), new File("target/test-classes/framework"));
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("繧ｳ繝斐・螟ｱ謨・);
		}
		//ServiceLocatorImpl.setModuleName("app/test-classes");
		arg0.put(EJBContainer.MODULES, new File[]{ new File("target/test-classes"),new File("./container-core-1.0.0-SNAPSHOT.jar"),new File("./service-api-ext-1.0.0-SNAPSHOT.jar")});
		container =EJBContainer.createEJBContainer(arg0);

	}

	/**
	 * 繧ｳ繝ｳ繝・く繧ｹ繝育ｴ譽・
	 */
	@AfterClass
	public static void tearDown(){
		container.close();
	}
	
}

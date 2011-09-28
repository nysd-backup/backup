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
	 * コンチE��スト�E期化
	 */
	@BeforeClass
	public static void initialize(){
		
		Map<String,Object> arg0 = new HashMap<String,Object>();
		
		//domain.xmlからjdbc-resourceなどの設定を読み込むための設宁E
		arg0.put("org.glassfish.ejb.embedded.glassfish.instance.root","C:\\Env\\Personal\\glassfishv3\\glassfish\\domains\\domain1");		
		arg0.put("org.glassfish.ejb.embedded.glassfish.installation.root","C:\\Env\\Personal\\glassfishv3\\glassfish");
		
		arg0.put(EJBContainer.APP_NAME, "app");
		
		//test-classesにclassesのソースを読ませることでtest/resource/ejb-jarとclassesのクラス両方のSessionBeanを作�E可能にする
		try{
			FileUtils.copyDirectory(new File("target/classes/framework"), new File("target/test-classes/framework"));
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("エラー");
		}
		//ServiceLocatorImpl.setModuleName("app/test-classes");
		arg0.put(EJBContainer.MODULES, new File[]{ new File("target/test-classes"),new File("./container-core-1.0.0-SNAPSHOT.jar"),new File("./service-api-ext-1.0.0-SNAPSHOT.jar")});
		container =EJBContainer.createEJBContainer(arg0);

	}

	/**
	 * コンチE��スト破棁E
	 */
	@AfterClass
	public static void tearDown(){
		container.close();
	}
	
}

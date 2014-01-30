/**
 * 
 */
package org.coder.dispatcher;

import org.coder.sample.s2jdbc.domain.Parent;
import org.coder.sample.s2jdbc.service.VersionService;
import org.junit.Ignore;
import org.junit.Test;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class Register{
	
    @Test
    public void test() throws Exception{
    	SingletonS2ContainerFactory.init();    	    	
    	VersionService ca = SingletonS2Container.getComponent(VersionService.class);        	    	
    	ca.register();
    	Parent parent = ca.find(); 
    	System.out.println(parent);
    }
    
}

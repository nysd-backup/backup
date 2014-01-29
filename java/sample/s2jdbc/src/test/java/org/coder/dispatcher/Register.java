/**
 * 
 */
package org.coder.dispatcher;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.coder.sample.s2jdbc.domain.Parent;
import org.coder.sample.s2jdbc.service.VersionService;
import org.junit.Test;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * @author yoshida-n
 *
 */
public class Register{
	
	@Test
	public void test2() throws Exception{
		ServerSocket socket = new ServerSocket(23000);
		Socket s = socket.accept();		
		s.getOutputStream().write(100);
	}
	
	@Test
	public void test3() throws Exception {
		Socket socket = new Socket("localhost",23000);
		socket.setKeepAlive(true);
		InputStream stream = socket.getInputStream();
		int value = -1;
		while((value = stream.read()) < -1){
			System.out.println(value);
		}
	}
	    
    @Test
    public void test() throws Exception{
    	SingletonS2ContainerFactory.init();    	    	
    	VersionService ca = SingletonS2Container.getComponent(VersionService.class);        	    	
    	ca.register();
    	Parent parent = ca.find(); 
    	System.out.println(parent);
    }
    
}

/**
 * Use is subject to license terms.
 */
package framework.service.ext.core;

import junit.framework.Assert;

import org.junit.Test;

import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.service.core.error.MessageAccessorImpl;
import framework.service.core.locator.ServiceLocator;
import framework.service.ext.tool.ContainerTestCase;

/**
 * function.
 *
 * @author	yoshida-n
 * @version	2011/02/20 new create
 */

public class ServiceLocatorTest extends ContainerTestCase{
	
	/**
	 * インターフェースタイプから実装の取得
	 * @throws Exception
	 */
	@Test
	public void lookupInterface() throws Exception{
		@SuppressWarnings("rawtypes")
		MessageAccessor holder = ServiceLocator.lookupByInterface(MessageAccessor.class);
		MessageBean message = holder.createMessage(100);
		Assert.assertEquals(100,message.getCode());
	}
	
	/**
	 * 実装の取得
	 * @throws Exception
	 */
	@Test
	public void lookupName() throws Exception{
		@SuppressWarnings("rawtypes")
		MessageAccessor holder = ServiceLocator.lookup(MessageAccessorImpl.class.getSimpleName());
		MessageBean message = holder.createMessage(100);
		Assert.assertEquals(100,message.getCode());
	}

}

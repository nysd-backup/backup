/**
 * Use is subject to license terms.
 */
package framework.api.message.impl;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import framework.core.message.BuildedMessage;
import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageBuilder;
import framework.logics.builder.impl.MessageBuilderImpl;

/**
 * function.
 *
 * @author	yoshida-n
 * @version	2011/02/20 new create
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MessageBuilderImplTest{
	
	/**
	 * リソースバンドル取得
	 * @throws Exception
	 */
	@Test
	public void load() throws Exception{
		
		MessageBuilder builder = new MessageBuilderImpl();
		DefinedMessage defined = builder.load(new MessageBean(100),null);
		Assert.assertEquals("TEST{0}",defined.getMessage());
		Assert.assertEquals("Warn",defined.getLevel().name());
		Assert.assertFalse(defined.isNotify());
		
	}
	
	/**
	 * ロケール指定のリソースバンドル取得
	 * @throws Exception
	 */
	@Test
	public void loadWithLocale() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		DefinedMessage defined = builder.load(new MessageBean(100), new Locale("en"));
		Assert.assertEquals("LOCALE_TEST{0}",defined.getMessage());
		Assert.assertEquals("Error",defined.getLevel().name());
		Assert.assertTrue(defined.isNotify());
	}
	
	/**
	 * ロケール指定のリソースバンドル取得
	 * @throws Exception
	 */
	@Test
	public void build() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		DefinedMessage defined = builder.load(new MessageBean(100,"TEST"), new Locale("en"));
		BuildedMessage builded = builder.build(defined);
		Assert.assertEquals(100,builded.getDefined().getMessageBean().getCode());
		Assert.assertTrue(builded.getDefined().isNotify());
		Assert.assertEquals("Error",builded.getDefined().getLevel().name());
	}

}

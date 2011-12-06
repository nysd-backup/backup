/**
 * Copyright 2011 the original author
 */
package framework.api.message.impl;

import java.util.Locale;

import junit.framework.Assert;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.logics.message.impl.MessageBuilderImpl;
import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.core.message.MessageBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;


/**
 * function.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MessageBuilderImplTest{
	
	/**
	 * リソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void load() throws Exception{
		
		MessageBuilder builder = new MessageBuilderImpl();
		MessageBean bean = new MessageBean(new ErrorMessage(0),new Locale("en"));
		String defined = builder.load(bean);
		Assert.assertEquals("TEST{0}",defined);
		
	}
	
	/**
	 * ロケール持E���Eリソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void loadWithLocale() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		MessageBean bean = new MessageBean(new ErrorMessage(100),new Locale("en"));
		String defined = builder.load(bean);
		Assert.assertEquals("LOCALE_TEST{0}",defined);
	}
	
	/**
	 * ロケール持E���Eリソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void build() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		MessageBean bean = new MessageBean(new ErrorMessage(100),new Locale("en"),100);
		String builded = builder.load(bean);
		Assert.assertEquals("LOCALE_TEST100",builded);
	}

}
